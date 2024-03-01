package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

import java.util.List;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.exception.WrongIndexException;

/**
 * Class that is strictly used to validate values of given indexes.
 * 
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.1
 */
public class IndexValidator {
    
    /**
     * Method used to check if given indexes are not bigger then the arrays size.
     * 
     * @param pieceIndex Index of pieces array which length is 3
     * @param xBoardIndex X index of the board which is a 2D array with length of 9
     * @param yBoardIndex Y index of the board which is a 2D array with length of 9
     * @throws pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.exception.WrongIndexException when index is invalid
     */
    public void validIndexes(int pieceIndex, int xBoardIndex, int yBoardIndex) 
            throws WrongIndexException{
        if(pieceIndex < 1  ||  pieceIndex > 3) { throw new WrongIndexException(); }
        if(xBoardIndex < 1 || xBoardIndex > 9) { throw new WrongIndexException(); }
        if(yBoardIndex < 1 || yBoardIndex > 9) { throw new WrongIndexException(); }
    }
    
    /**
     * Method that checks if given piece can fit on a board on given indexes. 
     * Checks if the 1s on the piece array do not stick out of the board array 
     * and checks if 1s on the piece do not collide with 1s on the board.
     * 
     * @param xBoardIndex X index of the board array
     * @param yBoardIndex Y index of the board array
     * @param board 2D array that holds current state of the board
     * @param piece 2D array that hold shape of the piece
     * @return True if the given piece can hold in the board on given indexes
     */
    public boolean pieceFits(int xBoardIndex, int yBoardIndex, List<List<Integer>> board, List<List<Integer>> piece){      
        if(board == null || piece == null) return false;
        
        //Check if 1s in a piece stick out of the board
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(piece.get(i).get(j) == 1){
                    if(i + yBoardIndex > 8 || j + xBoardIndex > 8){
                        return false;
                    }
                }
            }
        }
        
        //Check if 1s in piece collide with 1s on board
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(piece.get(i).get(j) == 1 && board.get(i+yBoardIndex).get(j + xBoardIndex) == 1){
                    return false;
                }
            }
        }
        
        return true;
    }
}
