package tools;

public interface PriorityQueue<Elt> {
  boolean isEmpty();
  void offer(Elt e);
  Elt poll();
}
