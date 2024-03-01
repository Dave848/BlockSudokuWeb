package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * Class that holds information about scoreboard.
 * 
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.1
 */
public class Scoreboard {
    /**
     * A private field that contains information about scores.
     */
    private List<Pair<String, Integer>> records;
    
    /**
     * Variable that is used to connect to database.
     */
    private Connection connection;
    
    /**
     * Parameter constructor. Initialization of records field and getting 
     * information from database.
     * 
     * @param con connection to database.
     */
    public Scoreboard(Connection con){
        this.records = new ArrayList<>();   
        this.connection = con;
        createTables();
        updateRecords();
    }
    
    /**
     * Private method that creates new tables if they are needed
     */
    private void createTables() throws NullPointerException{
        // Create users table
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE users "
                    + "(user_id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "username VARCHAR(30) NOT NULL)");
            System.out.println("Users table created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        } 
        
        // Create scores table
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE scores "
                    + "(score_id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "user_id INTEGER NOT NULL, "
                    + "score INTEGER NOT NULL)");
            System.out.println("Scores table created");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    
    /**
     * Method used to update records of scoreboard. Used when changes in database were made.
     */
    private void updateRecords(){
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM scores AS s "
                    + "INNER JOIN users AS u ON u.user_id = s.user_id");
            while (rs.next()) {
                Pair<String, Integer> record = new Pair(rs.getString("username"),
                                                        rs.getInt("score"));
                records.add(record);
            }
            rs.close();
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }
    
    /**
     * Method used to save score to database.
     * 
     * @param name username of player
     * @param score amount of points that player managed to achieve
     */
    public void saveScore(String name, Integer score) {
        // Check if there's no user with given name
        boolean isNameUnique = true;
        for(var record: records){
            if (record.getFirst() == name){
                isNameUnique = false;
                break;
            }
        }
        
        // Insert new user to database if there's no user with that name
        if(isNameUnique){
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO users (username) VALUES ('" + name + "')");
            } catch (SQLException sqle) {
                System.err.println(sqle.getMessage());
            }
        }
        
        // Insert score of the player into database
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO scores (user_id, score) VALUES "
                    + "((SELECT user_id FROM users "
                    + "WHERE username LIKE '" + name + "'), " + score + ")");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        
        updateRecords();
    }
    
    /**
     * Parameter constructor. Setting field of the class with values from given 
     * parameter.
     * 
     * @param newRecords Scores that are set as a field.
     */
    public Scoreboard(List<Pair<String, Integer>> newRecords){
        this.records = newRecords;
    }
    
    /**
     * Get records in form of 2D array of strings.
     * @return All records from scoreboard.
     */
    public String[][] getSortedRecords(){
        String[][] recordsInStrings = new String[records.size()][2];
        String[] temp;
        
        int counter = 0;
        for(Pair pair : records){
            temp = new String[]{ pair.getFirst().toString(),
                                 pair.getSecond().toString()};
            recordsInStrings[counter++] = temp;
        }
        
        java.util.Arrays.sort(recordsInStrings, (a,b)-> 
        Integer.compare(Integer.parseInt(b[1]), Integer.parseInt(a[1])));
        
        return recordsInStrings;
    }
}
