package cac7er;

import java.lang.ref.*;

import kotlin.jvm.functions.*;

import java.util.*;
import java.util.concurrent.*;

final class Uniformizer<T> {
   private static final Executor observerNotifierExecutor
         = Executors.newSingleThreadExecutor();

   final RepositoryImpl<?, T> repository;
   final String fileName;

   private T content;
   private CirculationRecord circulationRecord;

   private final List<WeakReference<Function2<Cache<? extends T>, ? super T, ?>>>
         observers = Collections.synchronizedList(
               new LinkedList<WeakReference<Function2<Cache<? extends T>, ? super T, ?>>>()
         );

   private final Map<Object, Function2<Cache<? extends T>, ? super T, ?>>
         observerAssociator = Collections.synchronizedMap(
               new WeakHashMap<Object, Function2<Cache<? extends T>, ? super T, ?>>()
         );

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

   final void addObserver
         (final Function2<Cache<? extends T>, ? super T, ?> observer)
   {
      observers.add(
            new WeakReference<Function2<Cache<? extends T>, ? super T, ?>>(observer)
      );
   }

   final void addObserver(
         final Object owner,
         final Function2<Cache<? extends T>, ? super T, ?> observer)
   {
      observers.add(
            new WeakReference<Function2<Cache<? extends T>, ? super T, ?>>(observer)
      );

      observerAssociator.put(owner, observer);
   }

   final void removeObserver
         (final Function2<Cache<? extends T>, ? super T, ?> observer)
   {
      synchronized (observers) {
         final Iterator<WeakReference<Function2<Cache<? extends T>, ? super T, ?>>>
               iter = observers.iterator();

         while (iter.hasNext()) {
            final Function2<Cache<? extends T>, ? super T, ?> o = iter.next().get();

            if (o == observer) {
               iter.remove();
            }
         }
      }

      synchronized (observerAssociator) {
         final Iterator<Map.Entry<Object, Function2<Cache<? extends T>, ? super T, ?>>>
               iter = observerAssociator.entrySet().iterator();

         while (iter.hasNext()) {
            final Function2<Cache<? extends T>, ? super T, ?> o = iter.next().getValue();

            if (o == observer) {
               iter.remove();
            }
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
            synchronized (observers) {
               final Iterator<WeakReference<Function2<Cache<? extends T>, ? super T, ?>>>
                     iter = observers.iterator();

               while (iter.hasNext()) {
                  final WeakReference<Function2<Cache<? extends T>, ? super T, ?>>
                        observerRef = iter.next();

                  final Function2<Cache<? extends T>, ? super T, ?>
                        observer = observerRef.get();

                  if (observer == null) {
                     iter.remove();
                     continue;
                  }

                  observer.invoke(cache, content);
               }
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
