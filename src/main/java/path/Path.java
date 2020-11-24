package path;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class Path<P> {

  public static <P> Path<P> empty() {
    return new EndOfPath<>();
  }

  public Path<P> append(P element) {
    return new ContinuingPath<>(element, this);
  }

  public abstract Optional<P> getFirst();
  public abstract Optional<Path<P>> getTail();

  public abstract boolean isEmpty();

  public abstract int length();


  public abstract void forEach(Consumer<P> action);

  public void forEachFromLast(Consumer<P> action) {
    Deque<P> queue = new ArrayDeque<>();
    this.forEach(queue::offerFirst);
    queue.forEach(action);
  };

}
