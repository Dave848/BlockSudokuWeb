package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Class used to test methods of the Board class.
 * 
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 */
public class BoardTest {

    private Board board;

    /**
     * Test of updateBoard method, of class Board.
     *
     * @param resultBoard Expected result
     * @param xIndex X index of the board
     * @param yIndex X index of the board
     * @param piece Piece that is being put on board
     */
    @ParameterizedTest
    @MethodSource
    public void testUpdateBoard(List<List<Integer>> resultBoard, int xIndex,
            int yIndex, List<List<Integer>> piece) {
        // Given
        this.board = new Board();

        // When
        this.board.updateBoard(piece, xIndex, yIndex);

        // Then
        assertEquals(resultBoard, this.board.getBoard(), "Board update failed.");
    }

    /**
     * Method that deliver arguments for test method.
     * @return Arguments for test method.
     */
    private static Stream<Arguments> testUpdateBoard() {
        return Stream.of(
                Arguments.of(new ArrayList<>() {
                    {
                        add(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                    }
                }, 0, 0,
                        new ArrayList<>() {
                    {
                        add(Arrays.asList(1, 1, 0));
                        add(Arrays.asList(1, 1, 0));
                        add(Arrays.asList(0, 0, 0));
                    }
                }),
                Arguments.of(new ArrayList<>() {
                    {
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 1, 1, 1, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                    }
                }, 5, 5,
                        new ArrayList<>() {
                    {
                        add(Arrays.asList(1, 1, 1));
                        add(Arrays.asList(0, 0, 0));
                        add(Arrays.asList(0, 0, 0));
                    }
                }),
                Arguments.of(new ArrayList<>() {
                    {
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                    }
                }, 50, 30,
                        new ArrayList<>() {
                    {
                        add(Arrays.asList(1, 1, 1));
                        add(Arrays.asList(0, 0, 0));
                        add(Arrays.asList(0, 0, 0));
                    }
                }),
                Arguments.of(new ArrayList<>() {
                    {
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                    }
                }, 1, 1, null)
        );
    }

    /**
     * Test of checkFullLines method, of class Board.
     *
     * @param newBoard Board that is being tested
     * @param actualScore Expected result
     */
    @ParameterizedTest
    @MethodSource
    public void testCheckFullLines(List<List<Integer>> newBoard, int actualScore) {
        // Given
        this.board = new Board();
        this.board.setBoard(newBoard);

        // When
        int resultScore = this.board.checkFullLines();

        // Then
        assertEquals(resultScore, actualScore, "Wrong score output.");
    }

    /**
     * Method that deliver arguments for test method.
     * @return Arguments for test method.
     */
    private static Stream<Arguments> testCheckFullLines() {
        return Stream.of(
                Arguments.of(new ArrayList<>() {
                    {
                        add(Arrays.asList(1, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 0, 0, 0, 1, 0, 0, 0, 0));
                        add(Arrays.asList(0, 1, 0, 1, 1, 1, 0, 0, 0));
                        add(Arrays.asList(0, 0, 1, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 1, 1, 0, 0, 0, 0));
                        add(Arrays.asList(0, 1, 1, 1, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));
                    }
                }, 0),
                Arguments.of(new ArrayList<>() {
                    {
                        add(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 0, 1, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 1, 1, 1, 0, 0, 0));
                        add(Arrays.asList(1, 1, 1, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 1, 1, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 1, 1, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0, 0));
                    }
                }, 40),
                Arguments.of(new ArrayList<>() {
                    {
                        add(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1));
                        add(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1));
                        add(Arrays.asList(1, 1, 0, 1, 1, 1, 0, 0, 0));
                        add(Arrays.asList(1, 1, 1, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 1, 1, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 1, 1, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0, 0));
                        add(Arrays.asList(1, 1, 0, 0, 0, 0, 0, 0, 0));
                    }
                }, 160)
        );
    }
}
