package cac7er;

final class Uniformizer<T> {
   final RepositoryImpl<?, T> repository;
   final String fileName;

   private T content;
   private CirculationRecord circulationRecord;

   private State state = State.EMPTY;

   Uniformizer(final RepositoryImpl<?, T> repository,
               final String fileName)
   {
      this.repository = repository;
      this.fileName   = fileName;
   }

   /**
    * @return content, or null if state == EMPTY
    */
   final T getContent() {
      if (state != State.INITIALIZING) return content;

      // shorthand of
      // if (state == State.INITIALIZED) return content;
      // if (state == State.EMPTY)       return null;

      synchronized (this) {
         while (state == State.INITIALIZING) {
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
         }
      }
   }

   final CirculationRecord getCirculationRecord() {
      if (state != State.INITIALIZING) return circulationRecord;

      // shorthand of
      // if (state == State.INITIALIZED) return circulationRecord;
      // if (state == State.EMPTY)       return null;

      synchronized (this) {
         while (state == State.INITIALIZING) {
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

   final synchronized void setLoadedContent(
         final T content,
         final CirculationRecord circulationRecord)
   {
      this.content           = content;
      this.circulationRecord = circulationRecord;

      state = State.INITIALIZED;
   }

   private static enum State {
      /** Empty. Only observers are available. */
      EMPTY,

      /** {@link CacheServiceKt#load(Uniformizer)} is running. */
      INITIALIZING,

      /** has a content and a circulationRecord. */
      INITIALIZED,
   }
}
