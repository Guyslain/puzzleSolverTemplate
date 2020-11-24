package solver;

import java.util.stream.Stream;

public interface Position<This extends Position<This,M>, M> {

  boolean isWinning();

  Stream<M> possibleMoves();

  This apply(M move);

  int score();

}
