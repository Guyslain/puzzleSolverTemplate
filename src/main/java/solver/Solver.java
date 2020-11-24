package solver;

import path.Path;
import tools.LifoPriorityQueue;
import tools.PriorityQueue;

import java.util.*;


public class Solver<P extends Position<P,M>, M> {

  PriorityQueue<PartialSolution<P,M>> waitingQueue;
  Map<P,Integer> knownPositions = new HashMap<>();
  private int nbNodes = 0;

  public Solver(P startingPosition) {
    Comparator<PartialSolution<P,M>> nbMovesComparator =
        Comparator.comparing(partial ->
            partial.getMoves().length()
            + partial.getPosition().score()
        );
    waitingQueue = new LifoPriorityQueue<>(nbMovesComparator);
    waitingQueue.offer(PartialSolution.starting(startingPosition));
  }


  public Optional<Path<M>> solve() {
    while (!waitingQueue.isEmpty()) {
      nbNodes++;
      PartialSolution<P,M> solution = waitingQueue.poll();
      if (!isImprovement(solution)) { continue; }
      P position = solution.getPosition();
      knownPositions.put(position, solution.length());
      position.possibleMoves()
          .map(move-> solution.move(move, position.apply(move)))
          .filter(this::isImprovement)
          .forEach(waitingQueue::offer);
      if (position.isWinning()) {
        return Optional.of(solution.getMoves());
      }
    }
    return Optional.empty();
  }

  public int getNbCheckedMoves() {
    return nbNodes;
  }


  private boolean isImprovement(PartialSolution<P, M> partialSolution) {
    P position = partialSolution.getPosition();
    return !knownPositions.containsKey(position)
        || knownPositions.get(position) > partialSolution.length();
  }

}
