/**
 * AI Principles and Techniques
 * Programming Assignment 2 - Sudoku solver
 * 23-11-2023
 * 
 * Student: Serge Airapetjan
 * S. number: s1038921
 * 
 * Student: E. Rafael Medel Sanchez
 * S. number: s1129468
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Game {
  private Sudoku sudoku;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public void showSudoku() {
    System.out.println(sudoku);
  }

  /**
    * Minimum remaining values heuristic
    *
    * @param first
    * @param second
    * @return whether the arc's domain of this is greater than the one of other
    */
    public Integer MRVHeuristic(Field first, Field second){  // when used O(n)
        if(first.getDomainSize() > second.getDomainSize())
            return 1;
        else if(first.getDomainSize() < second.getDomainSize())
            return -1;
        else
            return 0;  
    }
    
    /**
     * Degree Heuristic
     * 
     * @param first
     * @param second
     * @return whether the arc of this puts constraints on more variable than that of other
     */
    public Integer degreeHeuristic(Field first, Field second){
        int[] empties = {0,0};
        
        first.getNeighbours().forEach((nb) -> {empties[0] += (nb.getValue() == 0) ? 1 : 0;});
        second.getNeighbours().forEach((nb) -> {empties[1] += (nb.getValue() == 0) ? 1 : 0;});

        if(empties[0]>empties[1])
            return -1;
        else if(empties[0]<empties[1])
            return 1;
        else
            return 0;
    }

  /**
   * Implementation of the AC-3 algorithm
   * 
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve() {
    // If a heuristic needs to be used, comment the list and choose the right comparator in the priority queue
    // In case no heuristic needs to be used, comment the priority queue and use the array list instead
    Comparator<Arc> customComparator1 = ((arcs1, arcs2) -> MRVHeuristic(arcs1.getLeft(), arcs2.getLeft())); // MRVHeuristic
    Comparator<Arc> customComparator2 = ((arcs1, arcs2) -> degreeHeuristic(arcs1.getLeft(), arcs2.getLeft())); // degree heuristic
    PriorityQueue<Arc> arcs = new PriorityQueue<>(customComparator1);
    //ArrayList<Arc> arcs = new ArrayList<Arc>();

    // Initialize all arcs
    for (int row = 0; row < 9; row++){
      for (int col = 0; col < 9; col++){
        for (Field nb : this.sudoku.getBoard()[row][col].getNeighbours()) {

          arcs.add(new Arc(this.sudoku.getBoard()[row][col], nb));
        }
      }   
    }

    // AC-3
    int arc_counter = 0;
    while(!arcs.isEmpty()){
      arc_counter += 1;
      Arc curArc = arcs.poll();
      //Arc curArc = arcs.remove(0);
      if (curArc.revise()) {
        if (curArc.getLeft().getDomainSize() == 0){
          return false;
        }
        for (Field nb : curArc.getLeft().getNeighbours()) {
          if(nb != curArc.getRight()){
            Arc newArc = new Arc(nb, curArc.getLeft());
            if(!arcs.contains(newArc))
              arcs.add(newArc);
          }
        }
      }
    }
    System.out.println("arcs: " + arc_counter);
    return true;
  }

  /**
   * Checks the validity of a sudoku solution
   * 
   * @return true if the sudoku solution is correct
   */
  public boolean validSolution() {
    Field[][] board = this.sudoku.getBoard();

    // Initialize sets for rows, columns, and squares
    Set<Integer>[] rows = new HashSet[9];
    Set<Integer>[] cols = new HashSet[9];
    Set<Integer>[] square = new HashSet[9];
    for (int i = 0; i < 9; i++) {
        rows[i] = new HashSet<>();
        cols[i] = new HashSet<>();
        square[i] = new HashSet<>();
    }

    // Iterate through the Sudoku board
    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            int val = board[row][col].getValue();

            if (val < 1 || val > 9 || 
                rows[row].contains(val) || 
                cols[col].contains(val) || 
                square[row / 3 * 3 + col / 3].contains(val)) {
                return false; // Invalid Sudoku
            }

            rows[row].add(val);
            cols[col].add(val);
            square[row / 3 * 3 + col / 3].add(val);
        }
    }

    // Check if all rows, columns, and squares contain numbers 1 to 9
    for (int i = 0; i < 9; i++) {
        if (rows[i].size() != 9 || cols[i].size() != 9 || square[i].size() != 9) {
            return false; // Invalid Sudoku
        }
    }

    return true; // Valid Sudoku
  }
}
