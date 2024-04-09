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


public class App {
    public static void main(String[] args) throws Exception {
        start("Sudoku5.txt");
    }

    /**
     * Start AC-3 using the sudoku from the given filepath, and reports whether the sudoku could be solved or not, and how many steps the algorithm performed
     * 
     * @param filePath
     */
    public static void start(String filePath){
        Game game1 = new Game(new Sudoku(filePath));
        game1.showSudoku();

        if (game1.solve() && game1.validSolution()){
            System.out.println("Solved!");
        }
        else{
            System.out.println("Could not solve this sudoku :(");
        }
        game1.showSudoku();
    }
}
