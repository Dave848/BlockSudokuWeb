package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for holding information about the previous games and 
 * manipulating database connected with game history.
 *
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.0
 */
public class GameHistory {
    /**
     * Variable that is used to connect to database.
     */
    private Connection connection;
    
    /**
     * Variable that is used to change old board to new
     */
    private List<List<Integer>> boardBox;
    
    /**
     * Variable that is used to change old pieces to new ones
     */
    private List<List<List<Integer>>> pieceBoxes;
    
    /**
     * Variable that is used to change old score to new
     */
    private Integer score;
    
    /**
     * Parameter constructor.
     * 
     * @param con connection to database
     */
    public GameHistory(Connection con){
        this.connection = con;
        createTable();
    }

    /**
     * Create game history table.
     */
    private void createTable() {
        // Create table
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE games "
                + "(game_id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "user_id INTEGER NOT NULL, "
                + "board VARCHAR(81), "
                + "piece1 VARCHAR(9), "
                + "piece2 VARCHAR(9), "
                + "piece3 VARCHAR(9), "
                + "score INTEGER)");
            System.out.println("Users table created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }   
    }

    /**
     * Add new game to database.
     * 
     * @param name of the player.
     * @return id of the created game.
     */
    public Integer addNewGame(String name) {
        Integer gameID = null;
        
        // Check if user with that name exists. If not create one in database.
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE username LIKE '" + name + "'");
            if(!rs.next()){
                statement.executeUpdate("INSERT INTO users (username) VALUES ('" + name + "')");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        
        // Add new game
        try {
            PreparedStatement statement = connection.prepareStatement(
                      "INSERT INTO games (user_id) VALUES ("
                    + "(SELECT user_id FROM users WHERE username LIKE '" + name + "'))",
                    Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            gameID = generatedKeys.getInt(1);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        
        return gameID;
    }
    
    /**
     * Update game given its game id and parameters.
     * 
     * @param gameID of the game to be updated
     * @param board current representation of the board
     * @param pieces current representation of pieces
     * @param score of the player
     */
    public void updateGame(Integer gameID, Board board, List<Piece> pieces, Integer score) {
        String boardString = "";
        List<String> piecesString = new ArrayList<>();
        
        // Prepare board to be uploaded
        for(List<Integer> temp: board.getBoard()){
            for(Integer value : temp){
                boardString += value.toString();
            }
        }
        
        // Prepare pieces to be uploaded
        for(int i = 0; i < 3; i++){
            String tempString = new String("");
            Piece piece = pieces.get(i);
            for(List<Integer> temp: piece.getPieceBox()){
                for(Integer value : temp){
                    tempString += value.toString();
                }
             }
            piecesString.add(tempString);
        }
        
        // Update game in the database
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE games SET "
                    + "board  = '" + boardString + "', "
                    + "piece1 = '" + piecesString.get(0) + "', "
                    + "piece2 = '" + piecesString.get(1) + "', "
                    + "piece3 = '" + piecesString.get(2) + "', "
                    + "score  = " + score + " "
                    + "WHERE game_id = " + gameID);
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        
    }

    /**
     * Method used to get IDs of past games that user have played.
     * 
     * @param name of the player.
     * @return array of game IDs
     */
    public List<Integer> getPastGames(String name) {
        List<Integer> pastGames = new ArrayList<>();
                
        // Check if this user is in database
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE username LIKE '" + name + "'");
            if(!rs.next()){
                return pastGames;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            return pastGames;
        }
        
        // Get game IDs from database
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT game_id FROM games WHERE user_id = "
                    + "(SELECT user_id FROM users WHERE username LIKE '" + name + "')");
            while(rs.next()){
                pastGames.add(rs.getInt("game_id"));
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        
        return pastGames;
    }

    /**
     * Load values from game given its ID
     * 
     * @param newGameID id of the game to be loaded 
     */
    public void loadGame(Integer newGameID) {
        String boardString = "";
        String piece1 = "";
        String piece2 = "";       
        String piece3 = "";

        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM games WHERE game_id = " + newGameID);
            if(rs.next()){
                this.score = rs.getInt("score");
                boardString = rs.getString("board");
                piece1 = rs.getString("piece1");
                piece2 = rs.getString("piece2");
                piece3 = rs.getString("piece3");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        

        this.boardBox = stringToIntegerList(boardString, 9);
        this.pieceBoxes = new ArrayList<>();
        this.pieceBoxes.add(stringToIntegerList(piece1, 3));
        this.pieceBoxes.add(stringToIntegerList(piece2, 3));
        this.pieceBoxes.add(stringToIntegerList(piece3, 3));
    }
    
    private List<List<Integer>> stringToIntegerList(String str, int counter){
        StringBuilder strBuilder = new StringBuilder(str);
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < counter; i++){
            List<Integer> temp = new ArrayList<>();
            for(int j = 0; j < counter; j++){
                temp.add(Integer.parseInt(String.valueOf(strBuilder.charAt(j + i*counter))));
            }
            result.add(temp);
        }
        return result;
    }
    
    /**
     * Get method for board box field.
     * 
     * @return representation of board
     */
    public List<List<Integer>> getBoardBox() {
        return boardBox;
    }

    /**
     * Get method for pieceBoxes field.
     * 
     * @return representation of pieces
     */
    public List<List<List<Integer>>> getPieceBoxes() {
        return pieceBoxes;
    }

    /**
     * Get method for score field
     * 
     * @return players score
     */
    public Integer getScore() {
        return score;
    }
}
