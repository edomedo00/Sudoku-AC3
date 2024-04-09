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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Sudoku {
  private Field[][] board;

  Sudoku(String filename) {
    this.board = readsudoku(filename);
  }

  @Override
  public String toString() {
    String output = "╔═══════╦═══════╦═══════╗\n";
		for(int i=0;i<9;i++){
      if(i == 3 || i == 6) {
		  	output += "╠═══════╬═══════╬═══════╣\n";
		  }
      output += "║ ";
		  for(int j=0;j<9;j++){
		   	if(j == 3 || j == 6) {
          output += "║ ";
		   	}
         output += board[i][j] + " ";
		  }
		  
      output += "║\n";
	  }
    output += "╚═══════╩═══════╩═══════╝\n";
    return output;
  }

  /**
	 * Reads sudoku from file
	 * @param filename
	 * @return 2d int array of the sudoku
	 */
	public static Field[][] readsudoku(String filename) {
		assert filename != null && filename != "" : "Invalid filename";
		String line = "";
		Field[][] grid = new Field[9][9];
		try {
		FileInputStream inputStream = new FileInputStream(filename);
        Scanner scanner = new Scanner(inputStream);
        for(int i = 0; i < 9; i++) {
        	if(scanner.hasNext()) {
        		line = scanner.nextLine();
        		for(int j = 0; j < 9; j++) {
              int numValue = Character.getNumericValue(line.charAt(j));
              if(numValue == 0) {
                grid[i][j] = new Field();
              } else if (numValue != -1) {
                grid[i][j] = new Field(numValue);
        			}
        		}
        	}
        }
        scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("error opening file: " + filename);
		}
    addNeighbours(grid);
		return grid;
	}

  /**
   * Adds a list of neighbours to each field, i.e., arcs to be satisfied
   * @param grid
   */
  private static void addNeighbours(Field[][] grid) {
  for(int i = 0; i < grid.length; i++){
    for(int j = 0; j < grid[i].length; j++){
      List<Field> neighbours = new ArrayList<>();
      for(int x = 0; x < grid.length; x++){
        if (x != i) {
          neighbours.add(grid[x][j]);
        }
      }
      for(int y = 0; y < grid[i].length; y++){
        if (y != j) {
          neighbours.add(grid[i][y]);
        }
      }
      
      int row = 3*(i/3);
      int col = 3*(j/3);
      //System.out.println(row);
      //System.out.println(col);

      for (int x = row ; x < row + 3; x++){
        for(int y = col; y < col + 3; y++){
          //System.out.println("(" + x + "," + y + ")");
          if (x != i || y != j) {
            neighbours.add(grid[x][y]);
          }
        }
      }

      grid[i][j].setNeighbours(neighbours);
    }
  }

  }

  /**
	 * Generates fileformat output
	 */
	public String toFileString(){
    String output = "";
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        output += board[i][j].getValue();
      }
      output += "\n";
    }
    return output;
	}

  public Field[][] getBoard(){
    return board;
  }

  /**
   * Checks whether a move is valid
   * @param move
   * @param x
   * @param y
   * @return boolean for whether the move is valid
   */
  public Boolean validMove(int move, int x, int y){
    for(Field neighbour : board[x][y].getNeighbours()){
      if(neighbour.getValue() == move){
        return false;
      }
    }
    return true;
  }
}