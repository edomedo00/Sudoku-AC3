import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
  private Sudoku sudoku;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public void showSudoku() {
    System.out.println(sudoku);
  }

  /**
   * Implementation of the AC-3 algorithm
   * 
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve() {
    // TODO: implement AC-3
    List<List<Field>> arcs = new ArrayList<>();
    for (int row = 0; row < 9; row++){
      for (int col = 0; col < 9; col++){
        for (Field nb : this.sudoku.getBoard()[row][col].getNeighbours()) {
          List<Field> arc = new ArrayList<>();
          arc.add(this.sudoku.getBoard()[row][col]);  
          arc.add(nb);
          arcs.add(arc);
        }
      }   
    }

    
    while(!arcs.isEmpty()){
      List<Field> curArc = arcs.get(0);
      arcs.remove(curArc);
      if (revise(curArc)) {
        if (curArc.get(0).getDomainSize() == 0){
          return false;
        }
        for (Field nb : curArc.get(0).getNeighbours()) {
          if(nb!=curArc.get(1)){
            List<Field> newArc = new ArrayList<>();
            newArc.add(nb);
            newArc.add(curArc.get(0));
            arcs.add(newArc);
          }
        }
      }
    }
    return true;
  }

  public boolean revise(List<Field> arc) {
    boolean revised = false;
    for (Integer x : arc.get(0).getDomain()) {
      if (x == arc.get(1).getValue()){
        arc.get(0).removeFromDomain(x);
        revised = true;
      }
    }
    return revised;
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
//   public boolean validSolution() {
//     for(int row = 0; row < this.sudoku.getBoard().length; row++){
//       for(int col = 0; col < this.sudoku.getBoard()[row].length; col++){
//         List<Field> neighbours = this.sudoku.getBoard()[row][col].getNeighbours();
//         List<Integer> rows = new ArrayList<>();
//         List<Integer> cols = new ArrayList<>();
//         List<Integer> square = new ArrayList<>();
        
//         if(row == 7 && col == 7){
//         System.out.println(neighbours.size());
//                 System.out.println(neighbours);

// //          System.out.println();
//         }
//         for(Field neighbour : neighbours){
//           if(rows.size() != 8)
//             rows.add(neighbour.getValue());
//           else if(cols.size() != 8){
//             cols.add(neighbour.getValue());
//           }
//           else{
//             square.add(neighbour.getValue());
//           }
//         }
//         // Checks whether all elements from the neighbouring rows, cols and the square are distinct
//         if( rows.size() != rows.stream().distinct().filter(x -> x != 0).count() &&
//             cols.size() != cols.stream().distinct().filter(x -> x != 0).count() &&
//             square.size() != square.stream().distinct().filter(x -> x != 0).count()){
//           return false;
//         }
//       }
//     }
//     return true;
//   }
}
