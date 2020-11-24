package hanoi;

import solver.Helpers;

public class Main {

  public static void main(String[] args) {
    Helpers.solveAndShow(System.out, new Hanoi(10));
  }
}
