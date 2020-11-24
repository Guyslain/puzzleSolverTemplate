package hanoi;

import solver.Position;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Hanoi implements Position<Hanoi, HanoiMove> {

  private final int size;
  private final List<Deque<Integer>> stacks;

  public Hanoi(int n) {
    this.size = n;
    this.stacks = new ArrayList<>(3);
    stacks.add(decreasingIntsStack(n));
    stacks.add(new ArrayDeque<>());
    stacks.add(new ArrayDeque<>());
    this.hash = computeHashCode();
  }

  private static Deque<Integer> decreasingIntsStack(int n) {
    Deque<Integer> ints = new ArrayDeque<>();
    IntStream.range(0,n).forEach(ints::offerFirst);
    return ints;
  }

  private Hanoi(Hanoi position, HanoiMove move) {
    this.size = position.size;
    this.stacks = new ArrayList<>(3);
    for (Deque<Integer> stack : position.stacks) {
      this.stacks.add(new ArrayDeque<>(stack));
    }
    Integer value = this.stacks.get(move.fromStack()).removeLast();
    this.stacks.get(move.toStack()).offerLast(value);
    this.hash = computeHashCode();
  }



  @Override
  public boolean isWinning() {
    return stacks.get(0).isEmpty()
        && stacks.get(1).isEmpty()
        && equalsDeque(stacks.get(2), decreasingIntsStack(size));
  }


  @Override
  public Stream<HanoiMove> possibleMoves() {
    return ALL_POSSIBLE_MOVES.stream().filter(this::isValid);
  }

  private static final List<HanoiMove> ALL_POSSIBLE_MOVES =
      IntStream.range(0,3).boxed().flatMap(x ->
          IntStream.range(0, 3)
              .filter(y -> x != y)
              .mapToObj(y -> new HanoiMove(x, y))
      )
      .collect(Collectors.toList());

  private boolean isValid(HanoiMove move) {
    Deque<Integer> stackFrom = stacks.get(move.fromStack());
    Deque<Integer> stackTo = stacks.get(move.toStack());
    return move.fromStack() != move.toStack()
        && !stackFrom.isEmpty()
        && (stackTo.isEmpty() || stackFrom.peekLast() < stackTo.peekLast());
  }


  @Override
  public Hanoi apply(HanoiMove move) {
    return new Hanoi(this, move);
  }


  @Override
  public int score() {
    if (stacks.get(0).isEmpty() && stacks.get(1).isEmpty()) { return 0; }
    Deque<Integer> maxStack =
        stacks.get(0).isEmpty() ? stacks.get(1):
        stacks.get(1).isEmpty() ? stacks.get(0):
        stacks.get(0).getFirst() > stacks.get(1).getFirst() ? stacks.get(0) :
        stacks.get(1);
    int max = maxStack.getFirst();
    return size - stacks.get(2).size()
        + maxStack.size() - 1
        + 2 * (int) stacks.get(2).stream().filter(v -> v < max).count();
  }

  private static String dequeToString(Deque<Integer> deque) {
    return deque.stream()
        .map(i -> String.format("%d", i))
        .collect(Collectors.joining(" ", ">> ", ""));
  }

  @Override
  public String toString() {
    return
        stacks.stream()
        .map(Hanoi::dequeToString)
        .collect(Collectors.joining("\n","","\n"));
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (o == null || !(o.getClass().equals(this.getClass()))) {
      return false;
    }
    Hanoi hanoi = (Hanoi) o;
    if (this.size != hanoi.size) { return false; }
    for (int i = 0; i < stacks.size(); i++) {
      if (!equalsDeque(this.stacks.get(i), hanoi.stacks.get(i))) {
        return false;
      }
    }
    return true;
  }


  // Deque does not override equals, so Deque.equals is the identity of objects
  // We need our own method to test deque eqality.
  private static <T> boolean equalsDeque(Deque<T> queue1, Deque<T> queue2) {
    if (queue1.size() != queue2.size()) { return false; }
    List<T> copy1 = new ArrayList<>(queue1);
    List<T> copy2 = new ArrayList<>(queue2);
    return copy1.equals(copy2);
  }


  private final int hash;

  private int computeHashCode() {
    return Arrays.hashCode(
        stacks.stream()
        .map(ArrayList::new)
        .mapToInt(Objects::hashCode)
        .toArray()
    );
  }

  @Override
  public int hashCode() {
    return hash;
  }
}
