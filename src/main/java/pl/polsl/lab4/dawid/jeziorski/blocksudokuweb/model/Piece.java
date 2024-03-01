package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class responsible for holding information about piece and methods used to
 * manipulate it.
 *
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.1
 */
public class Piece {

    /**
     * Field that holds information about shape of the piece.
     */
    private List<List<Integer>> pieceBox;

    /**
     * Constructor of the Piece Class.
     */
    public Piece() {
        pieceBox = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            pieceBox.add(Arrays.asList(0, 0, 0));
        }
    }

    /**
     * Set method that sets the pieceBox field to given parameter.
     *
     * @param box 2D list that holds new shape of a piece
     */
    public void setPieceBox(List<List<Integer>> box) {
        this.pieceBox = box;
    }

    /**
     * Get method that returns pieceBox field.
     *
     * @return 2D list that holds values of piece
     */
    public List<List<Integer>> getPieceBox() {
        return this.pieceBox;
    }
}
