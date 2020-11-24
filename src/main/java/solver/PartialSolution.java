package solver;

import path.Path;

class PartialSolution<P,M> {

  private final Path<M> moves;
  private final P position;

  public static <P,M> PartialSolution<P,M> starting(P position) {
    return new PartialSolution<>(Path.empty(), position);
  }

  public PartialSolution(Path<M> moves, P position) {
    this.moves = moves;
    this.position = position;
  }

  public PartialSolution<P,M> move(M move, P destination) {
    return new PartialSolution<>(moves.append(move), destination);
  }

  public Path<M> getMoves() {
    return moves;
  }

  public P getPosition() {
    return position;
  }

  public int length() {
    return moves.length();
  }
}
