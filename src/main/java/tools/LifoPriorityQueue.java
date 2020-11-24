package tools;

import java.util.Comparator;

public class LifoPriorityQueue<Elt> implements PriorityQueue<Elt> {

  private final Comparator<Elt> eltComparator;
  private final java.util.PriorityQueue<Entry> queue;
  private int index = 0;

  public LifoPriorityQueue(Comparator<Elt> comparator) {
    this.eltComparator = comparator;
    this.queue = new java.util.PriorityQueue<Entry>();
  }

  @Override
  public boolean isEmpty() {
    return queue.isEmpty();
  }

  @Override
  public void offer(Elt e) {
    queue.add(new Entry(e,index++));
  }

  @Override
  public Elt poll() {
    return queue.poll().element;
  }


  private class Entry implements Comparable<Entry> {
    Elt element;
    int order;

    Entry(Elt element, int order) {
      this.element = element;
      this.order = order;
    }

    int getOrder() {
      return order;
    }

    public int compareTo(Entry entry) {
      int delta = eltComparator.compare(this.element, entry.element);
      return delta == 0 ? entry.order - this.order : delta;
    }
  }

}
