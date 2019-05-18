package cac7er;

import kotlin.jvm.functions.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

final class Uniformizer<T> {
   private static final Executor observerNotifierExecutor
         = Executors.newSingleThreadExecutor();

   final RepositoryImpl<?, T> repository;
   final String fileName;

   private T content;
   private CirculationRecord circulationRecord;

   private static final Function2[] EMPTY_OBSERVERS = new Function2[0];

   @SuppressWarnings("unchecked")
   private final AtomicReference<Function2<Cache<? extends T>, ? super T, ?>[]>
         observers = new AtomicReference<Function2<Cache<? extends T>, ? super T, ?>[]>(
               EMPTY_OBSERVERS);

   private final Cache<T> cacheForObserver = new CacheImpl<T>(this);

   /**
    * <pre>
    *    EMPTY ─┬(start to load cache )→ INITIALIZING ─(finished loading)→ INITIALIZED
    *           └(cache file not found)→ DELETED
    * </pre>
    */
   private State state = State.EMPTY;

   Uniformizer(final RepositoryImpl<?, T> repository,
               final String fileName)
   {
      this.repository = repository;
      this.fileName   = fileName;
   }

   /**
    * @return content
    * @throws IllegalStateException when state == EMPTY or DELETED
    */
   final T getContent() {
      final State state = this.state;

      if (state == State.INITIALIZED) return content;

      if (state == State.DELETED) {
         throw new IllegalStateException(
               "repository: " + repository.getName() + ", file: " + fileName);
      }

      synchronized (this) {
         while (this.state == State.INITIALIZING ||
                this.state == State.EMPTY)
         {
            try {
               wait();
            } catch (final InterruptedException e) {
               // ignore
            }
         }
      }

      return content;
   }

   /**
    * @return content or null if state == DELETED
    * @throws IllegalStateException when state == EMPTY
    */
   final T getWeakContent() {
      final State state = this.state;

      if (state == State.INITIALIZED) return content;
      if (state == State.DELETED)     return null;

      if (state == State.EMPTY) throw new IllegalStateException();

      synchronized (this) {
         while (this.state == State.INITIALIZING) {
            try {
               wait();
            } catch (final InterruptedException e) {
               // ignore
            }
         }
      }

      return content;
   }

   final void setContent(final T content) {
      if (state == State.INITIALIZED) {
         this.content = content;
         notifyObservers(cacheForObserver, content);
         return;
      }

      synchronized (this) {
         if (state == State.INITIALIZED) {
            this.content = content;
         } else {
            this.content = content;
            this.circulationRecord = new CirculationRecord();

            state = State.INITIALIZED;
            notifyAll();
         }
      }

      notifyObservers(cacheForObserver, content);
   }

   final State getState() {
      return state;
   }

   /**
    * @return CirculationRecord
    * @throws IllegalStateException when state == EMPTY or DELETED
    */
   final CirculationRecord getCirculationRecord() {
      final State state = this.state;

      if (state == State.INITIALIZED) return circulationRecord;

      if (state == State.EMPTY || state == State.DELETED) {
         throw new IllegalStateException();
      }

      synchronized (this) {
         while (this.state == State.INITIALIZING) {
            try {
               wait();
            } catch (final InterruptedException e) {
               // ignore
            }
         }
      }

      return circulationRecord;
   }

   final void onStartToLoadContent() {
      state = State.INITIALIZING;
   }

   final void setDeleted() {
      state = State.DELETED;
   }

   // ==========================================================================

   @SuppressWarnings("unchecked")
   final void addObserver
         (final Function2<Cache<? extends T>, ? super T, ?> observer)
   {
      while (true) {
         final Function2<Cache<? extends T>, ? super T, ?>[] oldArray
               = observers.get();

         final int newLen = oldArray.length + 1;

         final Function2<Cache<? extends T>, ? super T, ?>[] newArray;

         if (newLen == 1) {
            newArray = new Function2[] { observer };
         } else {
            newArray = new Function2[newLen];
            System.arraycopy(oldArray, 0, newArray, 0, newLen - 1);
            newArray[newLen - 1] = observer;
         }

         if (observers.compareAndSet(oldArray, newArray)) return;
      }
   }

   @SuppressWarnings("unchecked")
   final void removeObserver
         (final Function2<Cache<? extends T>, ? super T, ?> observer)
   {
      while (true) {
         final Function2<Cache<? extends T>, ? super T, ?>[] oldArray
               = observers.get();

         final int oldLen = oldArray.length;

         if (oldLen == 0) {
            return;
         } else if (oldLen == 1) {
            if (oldArray[0] == observer) {
               if (observers.compareAndSet(oldArray, EMPTY_OBSERVERS)) return;
            } else {
               return;
            }
         } else {
            int obsIdx = -1;

            for (int i = 0; i < oldLen; i++) {
               if (oldArray[i] == observer) {
                  obsIdx = i;
                  break;
               }
            }

            if (obsIdx < 0) return;

            final int newLen = oldArray.length - 1;

            final Function2<Cache<? extends T>, ? super T, ?>[] newArray
                  = new Function2[newLen];

            System.arraycopy(oldArray,          0, newArray,      0, obsIdx);
            System.arraycopy(oldArray, obsIdx + 1, newArray, obsIdx, newLen - obsIdx);

            if (observers.compareAndSet(oldArray, newArray)) return;
         }
      }
   }

   final synchronized void setLoadedContent(
         final T content,
         final CirculationRecord circulationRecord)
   {
      this.content           = content;
      this.circulationRecord = circulationRecord;

      state = State.INITIALIZED;
      notifyAll();

      notifyObservers(cacheForObserver, content);
   }

   private void notifyObservers(final Cache<T> cache, final T content) {
      observerNotifierExecutor.execute(new Runnable() {
         @Override
         public void run() {
            for (final Function2<Cache<? extends T>, ? super T, ?> observer
                  : observers.get())
            {
               observer.invoke(cache, content);
            }
         }
      });
   }

   static enum State {
      /** Empty. Only observers are available. */
      EMPTY,

      /** {@link CacheServiceKt#load(Uniformizer)} is running. */
      INITIALIZING,

      /** has a content and a circulationRecord. */
      INITIALIZED,

      /** The content has been deleted by {@link Cac7er#gc(long) GC}. */
      DELETED,
   }
}
