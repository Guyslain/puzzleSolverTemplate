package solver;

import path.Path;
import tools.RandomSelector;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class Helpers {


  private static <P extends Position<P,M>,M> void
      showPartialSolution(PrintStream out, P startingPosition, Path<M> moves)
  {
    int length = moves.length();
    out.println(startingPosition);
    Deque<M> remainingMoves = new ArrayDeque<>(length);
    moves.forEach(remainingMoves::offerFirst);
    for (M move : remainingMoves) {
      out.println(move);
      startingPosition = startingPosition.apply(move);
      out.println(startingPosition);
    }
    out.println("Number of moves: " + length);
  }


  public static <P extends Position<P,M>, M> void
    solveAndShow(PrintStream out, P initialConfig)
  {
    Solver<P, M> solver = new Solver<P,M>(initialConfig);
    Optional<Path<M>> solution = solver.solve();
    solution.ifPresent(sol -> showPartialSolution(out, initialConfig, sol));
    System.out.println("Nodes visited: " + solver.getNbCheckedMoves());
  }

  public static <M> void showPath(PrintStream out, Path<M> path) {
    path.forEachFromLast(move -> out.print(move + " "));
  }


  public static <P extends Position<P,M>, M> P randomize(P position, int n) {
    for (int i = 0; i < n; i++) {
      position =
          position.possibleMoves()
              .map(position::apply)
              .collect(new RandomSelector<P>())
              .orElse(position); // no possible move
    }
    return position;
  }
}
