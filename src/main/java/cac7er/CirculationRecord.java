package cac7er;

import java.io.*;
import java.util.LinkedList;

final class CirculationRecord {
   private static final int MAX_NODE_DEPTH = 255;

   private Node topNode;

   CirculationRecord() {
      topNode = Terminal.SINGLETON;
   }

   private CirculationRecord(final Node topNode) {
      this.topNode = topNode;
   }

   static int periodOf(final long time) {
      return (int) (time >>> 27);
   }

   static long startTimeOf(final int period) {
      return ((long) period) << 27;
   }

   final void add(final long time, final float accessCount) {
      if (Float.isNaN(accessCount)) return;

      topNode = added(topNode,
            /* shouldDrop = */ topNode.depth >= MAX_NODE_DEPTH,
            periodOf(time), accessCount);
   }

   /**
    * <pre>
    *    added([(2, 3.0f), (1, 2.3f)], 2, 0.3f) == [(2, 3.3f), (1, 2.3f)]
    *    added([(2, 3.0f), (1, 2.3f)], 3, 0.3f) == [(3, 0.3f), (2, 3.0f), (1, 2.3f)]
    *    added([(3, 3.0f), (1, 2.3f)], 2, 0.3f) == [(3, 3.0f), (2, 0.3f), (1, 2.3f)]
    *    added([(2, 3.0f), (1, 2.3f)], 1, 0.3f) == [(2, 3.0f), (1, 2.6f)]
    * </pre>
    */
   private static Node added(final Node node,
                             final boolean shouldDrop,
                             final int period,
                             final float accessCount)
   {
      if (node == Terminal.SINGLETON) {
         return new Section(period, accessCount, node);
      }

      final Section section = (Section) node;

      if (section.period == period) {
         return new Section(
               period,
               section.accessCount + accessCount,
               section.older);
      }

      if (section.period < period) {
         if (shouldDrop) {
            return new Section(period, accessCount, section);
         } else {
            return new Section(period, accessCount, droppedOldest(section));
         }
      }

      return new Section(section.period, section.accessCount,
            added(section.older, shouldDrop, period, accessCount));
   }

   /**
    * <pre>
    *    droppedOldest([(3, 3.0f), (2, 2.2f), (1, 5.2f)]) == [(3, 3.0f), (2, 2.2f)]
    *    droppedOldest([(1, 5.2f)]) == []
    * </pre>
    */
   private static Node droppedOldest(final Section section) {
      final Node older = section.older;

      if (older == Terminal.SINGLETON) return Terminal.SINGLETON;

      return new Section(
            section.period,
            section.accessCount,
            droppedOldest((Section) older));
   }

   /**
    * @param periodList
    *   For performance, <b>Must be sorted by desc. No longer available after
    *   this calling.</b> This function has a side effect.
    */
   final void removeAll(final LinkedList<Integer> periodList) {
      topNode = filtered(topNode, periodList);
   }

   private static Node filtered(final Node node,
                                final LinkedList<Integer> periodList)
   {
      if (node == Terminal.SINGLETON) return node;

      final Section section = (Section) node;

      final int period = periodList.getFirst();

      if (section.period == period) {
         periodList.removeFirst();
         return filtered(section.older, periodList);
      }

      if (section.period > period) {
         periodList.removeFirst();
      }

      return new Section(section.period, section.accessCount,
            filtered(section.older, periodList));
   }

   final void writeTo(final RandomAccessFile file) throws IOException {
      file.writeByte(topNode.depth);
      writeTo(file, topNode);
   }

   private static void writeTo(final RandomAccessFile file, final Node node)
         throws IOException
   {
      if (node == Terminal.SINGLETON) return;

      Section section = (Section) node;

      writeTo(file, section.older);

      file.writeInt(section.period);
      file.writeFloat(section.accessCount);
   }

   static CirculationRecord readFrom(final RandomAccessFile file)
         throws IOException
   {
      final int depth = file.readUnsignedByte();

      Node topNode = Terminal.SINGLETON;

      for (int i = 0; i < depth; i++) {
         final int period = file.readInt();
         final float accessCount = file.readFloat();

         topNode = new Section(period, accessCount, topNode);
      }

      return new CirculationRecord(topNode);
   }

   @Override
   public final boolean equals(final Object o) {
      if (!(o instanceof CirculationRecord)) return false;

      return equals(topNode, ((CirculationRecord) o).topNode);
   }

   private static boolean equals(final Node node1, final Node node2) {
      if (node1 == node2) return true;
      if (node1.depth != node2.depth) return false;

      // if both nodes are Terminal, node1 == node2 is true so unreachable here.
      if (!(node1 instanceof Section)) return false;
      if (!(node2 instanceof Section)) return false;

      final Section section1 = (Section) node1;
      final Section section2 = (Section) node2;

      return section1.period == section2.period &&
            Math.abs(section1.accessCount - section2.accessCount) < .001f &&
            equals(section1.older, section2.older);
   }

   @Override
   public final int hashCode() {
      final Node topNode = this.topNode;

      if (topNode == Terminal.SINGLETON) return 0;

      final Section section = (Section) topNode;

      return section.depth | section.period |
            Float.floatToIntBits(section.accessCount);
   }

   private static abstract class Node {
      final int depth;

      Node(final int depth) {
         this.depth = depth;
      }
   }

   private static final class Terminal extends Node {
      static final Terminal SINGLETON = new Terminal();

      private Terminal() {
         super(0);
      }
   }

   private static final class Section extends Node {
      final int period;
      final float accessCount;
      final Node older;

      Section(final int period, final float accessCount, final Node older) {
         super(older.depth + 1);

         this.period      = period;
         this.accessCount = accessCount;
         this.older       = older;
      }
   }
}
