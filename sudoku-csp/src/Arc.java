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

import java.util.List;

public class Arc {
    private Field left;
    private Field right;

    Arc(Field left, Field right){
        this.left = left;
        this.right = right;
    }

    /**
    * Revise function
    * 
    * @return whether the value of right is removed from the domain of left
    */
    public boolean revise() {
        if(left.getDomain().contains(right.getValue())){
            return left.removeFromDomain(right.getValue());
        }
        return false;
    }

    /**
    * Getter for left
    * 
    * @return the value of left
    */
    public Field getLeft(){
        return left;
    }

    /**
    * Getter for right
    * 
    * @return the value of right
    */
    public Field getRight(){
        return right;
    }
}