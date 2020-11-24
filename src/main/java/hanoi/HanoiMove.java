package hanoi;

public class HanoiMove {
  private final int fromStack;
  private final int toStack;

  public int fromStack() {
    return fromStack;
  }

  public int toStack() {
    return toStack;
  }

  public HanoiMove(int fromStack, int toStack) {
    this.fromStack = fromStack;
    this.toStack = toStack;
  }

  @Override
  public String toString() {
    return fromStack + " -> " + toStack;
  }
}
