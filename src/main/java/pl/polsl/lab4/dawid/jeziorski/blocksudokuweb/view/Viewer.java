package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.view;

import java.io.PrintWriter;
import java.util.List;

/**
 * Class used to provide html strings, so website can get information.
 * 
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.3
 */
public class Viewer {
    /**
     * Method used to print starting strings of HTML.
     * 
     * @param out used to print html string
     */
    public void printStart(PrintWriter out){
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Block Sudoku</title>");
        out.println(" <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">");
        out.println("</head>");
        out.println("<body>");
    }  
    
    /**
     * Method used to print ending strings of HTML.
     * 
     * @param out used to print html string
     */
    public void printEnd(PrintWriter out){
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
     * Method used to print board on website as strings of HTML.
     * 
     * @param out used to print html string
     * @param board to be printed
     */
    public void printBoard(PrintWriter out, List<List<Integer>> board){
        out.println("<div class=\"board\">");
        for(List<Integer> row: board){
            for(Integer number: row){
                out.println("<div class=\"tile board-tile " + (number == 1? "black":"grey") + "\"></div>");
            }
        }
        out.println("</div>");
    }
    
    /**
     * Method used to print piece on website as strings of HTML.
     * 
     * @param out used to print html string
     * @param piece to be printed
     */
    public void printPiece(PrintWriter out, List<List<Integer>> piece) {
        out.println("<div class=\"piece\">");
        for(List<Integer> row: piece){
            for(Integer number: row){
                out.println("<div class=\"tile piece-tile " + (number == 1? "black":"grey") + "\"></div>");
            }
        }
        out.println("</div>");
    }

    /**
     * Method used to print control panel on website as strings of HTML.
     * Control panel is used to select piece and board indexes.
     * 
     * @param out used to print html string
     */
    public void printControlPanel(PrintWriter out) {
        out.println("<form action=\"update\" class=\"form-container\">");
        out.println("<label>Select piece: <input type=\"text\" name=\"pieceIndex\" placeholder=\"1-3\" required class=\"index-input\"/></label>");
        out.println("<label>Select row: <input type=\"text\" name=\"yIndex\" placeholder=\"1-9\" required class=\"index-input\"/></label>" );
        out.println("<label>Select column: <input type=\"text\" name=\"xIndex\" placeholder=\"1-9\" required class=\"index-input\"/></label>"); 
        out.println("<input class=\"button\" type=\"submit\" value=\"Place piece\"/>");
        out.println("</form>");
    }

    /**
     * Method used to print users score on website as strings of HTML.
     *
     * @param out used to print html string
     * @param score to be printed
     */
    public void printScore(PrintWriter out, Integer score) {
        out.println("<p> Score: " + score + " </p>");
    }
    
    /**
     * Method used to print scoreboard button on website as strings of HTML.
     * Clicking scoreboard button results on moving into /scoreboard website.
     * 
     * @param out used to print html string
     */
    public void printScoreboardButton(PrintWriter out) {
        out.println("<form action=\"scoreboard\">");
        out.println("<input class=\"button\" type=\"submit\" value=\"Scoreboard\"/>");
        out.println("</form>");
    }
    
    /**
     * Method used to print information about wrong user input on website 
     * as strings of HTML.
     * 
     * @param out used to print html string
     */
    public void printWrongInput(PrintWriter out){
        out.println("<p>Wrong user input. Try again.<p>");
    }
    
    /**
     * Method used to print information about game end on website 
     * as strings of HTML.
     * 
     * @param out used to print html string
     * @param score of the player
     */
    public void printGameFinished(PrintWriter out, Integer score) {
        out.println("<div class=\"center-div\">");
        out.println("<h1> Congratulations! </h1> <br>");
        out.println("Your score was: " + score);
        out.println("<br> Score saved!");
        out.println("</div>");
    }

    /**
     * Method used to print one of the records from scores file on website 
     * as strings of HTML.
     * 
     * @param out used to print html string
     * @param name of the player
     * @param score pf the player 
     */
    public void printRecord(PrintWriter out, String name, String score) {
        out.println("<p>" + name + "   " + score + "</p>");   
    }
    
    /**
     * Method used to print back button. Clicking this button results in changing
     * site to /game.
     * 
     * @param out used to print html string
     */
    public void printBackButton(PrintWriter out) {
        out.println("<form action=\"game\">");
        out.println("<input type=\"submit\" value=\"Back\" class=\"button\" />");
        out.println("</form>");
    }
    
    /**
     * Method used to get path to templates file.
     * 
     * @param out used to print html string
     */
    public void printGetTemplates(PrintWriter out) {
        out.println("<div class=\"center-div\">");
        out.println("<h1> Templates not loaded </h1> <br>");
        out.println("<form action=\"game\">");
        out.println("<label>Templates file path: <input type=\"text\" name=\"templatesFile\" required /></label> <br> <br>"); 
        out.println("<input type=\"submit\" value=\"Save\" class=\"button\" />");
        out.println("</form>");
        out.println("</div>");
    }

    /**
     * Method used to get path to scores file.
     * 
     * @param out used to print html string
     */
    public void printGetScores(PrintWriter out) {
        out.println("<div class=\"center-div\">");
        out.println("<h1> Scores not loaded </h1> <br>");
        out.println("<form action=\"game\">");
        out.println("<label>Scores file path: <input type=\"text\" name=\"scoresFile\" required /></label> <br> <br>"); 
        out.println("<input type=\"submit\" value=\"Save\" class=\"button\" />");
        out.println("</form>");
        out.println("</div>");
    }

    /**
     * Method used to show game history button to user.
     * 
     * @param out used to print html string
     */
    public void printGameHistoryButton(PrintWriter out) {
        out.println("<form action=\"previous-games\">");
        out.println("<input type=\"submit\" value=\"Previous games\" class=\"button space-up\" />");
        out.println("</form>");
    }
    
    /**
     * Method used to show user that database is not available.
     * 
     * @param out used to print html string
     */
    public void printDatabaseUnavailable(PrintWriter out) {
        out.println("<p>Database is not available</p>");   
    }

    /**
     * Method used to create vertical blank space on website.
     * 
     * @param out used to print html string
     */
    public void printBlankSpace(PrintWriter out) {
        out.println("<br/>");
    }

    /**
     * Method used to show user IDs of his past games and let him choose which one
     * he wants to continue.
     * 
     * @param out used to print html string
     * @param pastGames array of IDs of player's past games
     */
    public void printPastGames(PrintWriter out, List<Integer> pastGames) {
        out.println("<div class=\"game-id-form\">");
        out.println("<p>From database</p>");
        out.println("<form action=\"load-game\">");
        for(Integer gameID : pastGames){
            out.println("<div>");
            out.println("<input type=\"radio\" id=\"" + gameID + "\" name=\"gameID\" value=\"" + gameID + "\" required />");
            out.println("<label for=\"" + gameID + "\"> Game ID: " + gameID + "</label>");
            out.println("</div>");
        }
        out.println("<input type=\"submit\" value=\"Load game\" class=\"button\" />");
        out.println("</form>");
        out.println("</div>");
    }
}
