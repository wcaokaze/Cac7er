package cac7er;

final class Uniformizer<T> {
   final RepositoryImpl<?, T> repository;
   final String fileName;

   private T content;
   private CirculationRecord circulationRecord;

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
            } catch (InterruptedException e) {
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
            } catch (InterruptedException e) {
               // ignore
            }
         }
      }

      return content;
   }

   final void setContent(T content) {
      if (state == State.INITIALIZED) {
         this.content = content;
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
            } catch (InterruptedException e) {
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

   final synchronized void setLoadedContent(
         final T content,
         final CirculationRecord circulationRecord)
   {
      this.content           = content;
      this.circulationRecord = circulationRecord;

      state = State.INITIALIZED;
      notifyAll();
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
