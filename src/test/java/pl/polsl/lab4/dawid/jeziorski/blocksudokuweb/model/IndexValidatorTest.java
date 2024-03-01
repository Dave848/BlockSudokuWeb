package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Class used to test methods of the IndexValidator class.
 * 
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 */
public class IndexValidatorTest {

    private IndexValidator validator;

    /**
     * Test of validIndexes method, of class IndexValidator.
     *
     * @param pieceIndex Index of a piece
     * @param xBoardIndex X index of the board
     * @param yBoardIndex Y index of the board
     * @param expectedResult Expected result of testing values
     */
    @ParameterizedTest
    @MethodSource
    public void testValidIndexes(int pieceIndex, int xBoardIndex, int yBoardIndex, boolean expectedResult) {
        // Given
        validator = new IndexValidator();
        boolean result = true;

        // Then
        try {
            validator.validIndexes(pieceIndex, xBoardIndex, yBoardIndex);
        } catch (Exception e) {
            result = false;
        }
        assertEquals(expectedResult, result, "Index validation failed.");
    }

    /**
     * Method that deliver arguments for test method.
     * @return Arguments for test method.
     */
    private static Stream<Arguments> testValidIndexes() {
        return Stream.of(
                Arguments.of(0, 0, 0, false),
                Arguments.of(-1, -1, -3, false),
                Arguments.of(30, 5, 7, false),
                Arguments.of(1, 5, 7, true)
        );
    }

    /**
     * Test of pieceFits method, of class IndexValidator.
     *
     * @param xBoardIndex X index of the board
     * @param yBoardIndex Y index of the board
     * @param expectedResult Expected result of testing values
     */
    @ParameterizedTest
    @MethodSource
    public void testPieceFits(int xBoardIndex, int yBoardIndex, boolean expectedResult) {
        // Given
        validator = new IndexValidator();
        List<List<Integer>> board = new ArrayList<>() {
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
        };

        List<List<Integer>> piece = new ArrayList<>() {
            {
                add(Arrays.asList(1, 0, 0));
                add(Arrays.asList(1, 0, 0));
                add(Arrays.asList(1, 1, 0));
            }
        };

        // When
        boolean result = validator.pieceFits(xBoardIndex, yBoardIndex, board, piece);

        // Then
        assertEquals(expectedResult, result, "Piece fits method failed.");

    }

    /**
     * Method that deliver arguments for test method.
     * @return Arguments for test method.
     */
    private static Stream<Arguments> testPieceFits() {
        return Stream.of(
                Arguments.of(50, -3, false),
                Arguments.of(0, 0, false),
                Arguments.of(8, 8, false),
                Arguments.of(6, 1, true)
        );
    }

    /**
     * Test of pieceFits method, of class IndexValidator. Tests null as a
     * parameter.
     *
     * @param xBoardIndex X index of the board
     * @param yBoardIndex Y index of the board
     * @param expectedResult Expected result of testing values
     * @param piece Piece that is tried to put on a board
     * @param board State of board
     */
    @ParameterizedTest
    @MethodSource
    public void testPieceFitsWhenNull(int xBoardIndex, int yBoardIndex,
            boolean expectedResult, List<List<Integer>> piece, List<List<Integer>> board) {
        // Given
        validator = new IndexValidator();

        // When
        boolean result = validator.pieceFits(xBoardIndex, yBoardIndex, board, piece);

        // Then
        assertEquals(expectedResult, result, "Nulls as parameters of Piece Fits.");

    }

    /**
     * Method that deliver arguments for test method.
     * @return Arguments for test method.
     */
    private static Stream<Arguments> testPieceFitsWhenNull() {
        return Stream.of(
                Arguments.of(1, 2, false, null, null),
                Arguments.of(1, 2, false, new ArrayList<>() {
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
                }, null),
                Arguments.of(1, 2, false, null, new ArrayList<>() {
                    {
                        add(Arrays.asList(1, 1, 0));
                        add(Arrays.asList(1, 1, 0));
                        add(Arrays.asList(0, 0, 0));
                    }
                }),
                Arguments.of(1, 2, true, new ArrayList<>() {
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
                }, new ArrayList<>() {
                    {
                        add(Arrays.asList(1, 1, 0));
                        add(Arrays.asList(1, 1, 0));
                        add(Arrays.asList(0, 0, 0));
                    }
                })
        );
    }
}
