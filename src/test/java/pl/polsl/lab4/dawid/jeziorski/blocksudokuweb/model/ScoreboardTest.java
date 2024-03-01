package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Class used to test methods of the Scoreboard class.
 * 
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 */
public class ScoreboardTest {
    private Scoreboard scoreboard;
    
    /**
     * Test of getSortedRecords method, of class Scoreboard.
     */
    @ParameterizedTest
    @MethodSource
    public void testGetSortedRecords(List<Pair<String, Integer>> records, String[][] expected) {
        // Given
        scoreboard = new Scoreboard(records);
        
        // When
        String[][] actual = scoreboard.getSortedRecords();
        
        // Then
        assertTrue(Arrays.deepEquals(actual, expected), "Method should return sorted records.");
    }
    
    /**
     * Method that deliver arguments for test method.
     * 
     * @return Arguments for test method.
     */
    private static Stream<Arguments> testGetSortedRecords() {
        return Stream.of(
        Arguments.of(
            new ArrayList<>() {
            {
                add(new Pair("Dawid", 120));
                add(new Pair("Kuba", 130));
                add(new Pair("Kamila", 590));
            }},  
            new String[][] {
                {"Kamila", "590"},
                {"Kuba", "130"},
                {"Dawid", "120"}
            }
            ),
        Arguments.of(
            new ArrayList<>() {{}},  
            new String[][] {}
            )
        );
    }
    
}
