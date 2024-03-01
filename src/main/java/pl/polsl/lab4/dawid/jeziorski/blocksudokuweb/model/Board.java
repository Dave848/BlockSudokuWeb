package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class responsible for holding information about the main board of the game
 * and manipulating it.
 *
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.2
 */
public class Board {

    /**
     * 2D Array of integers that holds information about the board.
     */
    private List<List<Integer>> board;

    /**
     * Enum used to manage lines deleting.
     */
    private enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    /**
     * Constructor used to initialize the board field.
     *
     */
    public Board() {
        board = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            board.add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
        }
    }

    /**
     * Set method for the board field.
     *
     * @param board 2D list that is the representation of the board.
     */
    public void setBoard(List<List<Integer>> board) {
        this.board = board;
    }

    /**
     * Get method for the board field.
     *
     * @return 2D list that is the representation of the board.
     */
    public List<List<Integer>> getBoard() {
        return this.board;
    }

    /**
     * Method that takes care of updating the board given appropriate indexes,
     * and 2D array. Indexes are used to set the coordinates on which the board
     * is being changed.
     *
     * @param piece 2D integer array that says how the board has to change
     * @param xIndex X coordinate of the board
     * @param yIndex Y coordinate of the board
     */
    public void updateBoard(List<List<Integer>> piece, int xIndex, int yIndex) {
        List<Integer> tempList = new ArrayList<>();

        if (piece == null) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            if (i + yIndex > 8) {
                continue;
            }
            tempList.addAll(board.get(i + yIndex));
            for (int j = 0; j < 3; j++) {
                if (piece.get(i).get(j) == 1) {
                    tempList.set(j + xIndex, piece.get(i).get(j));
                }
            }
            board.set(i + yIndex, tempList);
            tempList = new ArrayList<>();
        }
    }

    /**
     * Method that checks if any of the lines (vertically and horizontally) are
     * full of 1s. If line of 1s is found, it is being converted to line of 0s.
     * For every line deleted user gets more score.
     *
     * @return Value that is used as a score.
     */
    public int checkFullLines() {
        int linesDeleted = 0;
        List<Integer> fullArray = Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1);
        List<Pair<Integer, Orientation>> listToDelete = new ArrayList<>();

        // Check horizontally
        for (int i = 0; i < 9; i++) {
            if (board.get(i).equals(fullArray)) {
                linesDeleted += 1;
                listToDelete.add(new Pair(i, Orientation.HORIZONTAL));
            }
        }

        //Check vertically
        boolean foundZero = false;
        List<Integer> tempList = new ArrayList<>();
        for (int column = 0; column < 9; column++) {
            for (int row = 0; row < 9; row++) {
                tempList.addAll(board.get(row));
                if (tempList.get(column) == 0) {
                    foundZero = true;
                    break;
                }
                tempList.clear();
            }

            if (foundZero) {
                foundZero = false;
            } else {
                linesDeleted++;
                foundZero = false;
                listToDelete.add(new Pair(column, Orientation.VERTICAL));
            }
        }

        deleteLines(listToDelete);
        return linesDeleted * linesDeleted * 10;
    }

    /**
     * Method that handles the deletion of lines.
     *
     * @param listToDelete list of pairs that says which lines have to be
     * deleted.
     */
    private void deleteLines(List<Pair<Integer, Orientation>> listToDelete) {
        listToDelete.forEach(pair -> {
            switch (pair.getSecond()) {
                case HORIZONTAL:
                    board.set(pair.getFirst(),
                            Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                    break;
                case VERTICAL:
                    for (int i = 0; i < 9; i++) {
                        List<Integer> tempArray = board.get(i);
                        tempArray.set(pair.getFirst(), 0);
                        board.set(i, tempArray);
                    }
                    break;
            }
        });
    }
    
    /**
     * Method used to check if board is empty.
     * 
     * @return boolean value that says if board is empty (full of 0s)
     */
    public boolean isEmpty(){
        for(List<Integer> temp : board){
            for(Integer value : temp){
                if(1 == value)
                    return false;
            }
        }
        return true;
    }
}
