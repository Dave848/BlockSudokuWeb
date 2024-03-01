package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.Board;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.Piece;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.PieceTemplates;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.Scoreboard;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.GameHistory;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.view.Viewer;

/**
 * Class that is responsible for showing user the game and holding game information:
 * piece state, board state, score etc.
 * 
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.1
 */
@WebServlet(name = "game", urlPatterns = {"/game"})
public class GameServlet extends HttpServlet {
    
    /**
     * Object of the Viewer class, used to display different informations for
     * user.
     */
    private Viewer viewer;

    /**
     * Object of the Board class, used to hold information of the game board and
     * methods that can manipulate the board.
     */
    private Board board;

    /**
     * Array of the Piece class objects, used to hold information about pieces.
     */
    private List<Piece> pieces;

    /**
     * Object of the PieceTemplates class, used to set piece shapes.
     */
    private PieceTemplates pieceTemplates;
    
    /**
     * Object of the Scoreboard class, used to hold information about last games.
     */
    private Scoreboard scoreboard;
    
    /**
     * History of all the games. Class connected to database.
     */
    private GameHistory gameHistory;
    
    /**
     * Id of the game in database.
     */
    private Integer gameID;

    /**
     * Object used to store the score of the player.
     */
    private Integer score;
    
    /**
     * Object that holds actual session.
     */
    private HttpSession session;
    
    /**
     * Name of the player.
     */
    private String username;
    
    /**
     * Variable that checks if players input was correct.
     */
    private boolean wrongInput;
    
    /**
    * Variable that checks if the game is finished.
    */
    private boolean gameFinished;
    
    /**
     * Array that contains cookies.
     */
    private Cookie[] cookies;
    
    /**
     * A class field that contains information about database.
     */
    private boolean isDatabaseAvailable;
   
    /**
     * Variable that says if the piece templates were loaded or not.
     */    
    private boolean templatesLoaded;
    
    /**
     * Initialization method of game servlet.
     * 
     * @param config servlet configuration
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init (ServletConfig config) throws ServletException {
        super.init(config);
        
        // Check driver
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException ex) {
            System.err.println("Client driver not found");
        }
        
        // Set database
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/blocksudoku", "app", "app");
            this.scoreboard = new Scoreboard(con);
            this.gameHistory = new GameHistory(con);
            isDatabaseAvailable = true;
        } catch (NullPointerException ex){
            isDatabaseAvailable = false;
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
            isDatabaseAvailable = false;
        } 
        
        this.viewer = new Viewer();
        this.board = new Board();
        this.score = 0;
        
        // Initialize pieces array
        this.pieces = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            this.pieces.add(new Piece());
        }
        
        // Initialize templates
        try {
            this.pieceTemplates = new PieceTemplates(getServletContext().getRealPath("/templates.txt"));
            this.templatesLoaded = true;
        } catch (FileNotFoundException e) {
            this.templatesLoaded = false;
        }
        
        // Initialize pieces with templates
        if(templatesLoaded){
            for (Piece piece : pieces) {
                piece.setPieceBox(pieceTemplates.getRandomTemplate());
            }
        }
    }
       
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Uses Viewer class to print values on to the website.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Set cookie as a user name.
        if (cookies == null) {
            this.username = request.getParameter("username");
            cookies = request.getCookies();
            Cookie cookie = new Cookie("username", request.getParameter("username"));
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
        }
        
        // Load templates if they aren't loaded
        if (!templatesLoaded){
            if(request.getParameter("templatesFile") == null){
                response.sendRedirect("get-templates-file");
                return;
            }
            else{
                try {
                    this.pieceTemplates = new PieceTemplates(request.getParameter("templatesFile"));
                    this.templatesLoaded = true;
                    for (Piece piece : pieces) {
                        piece.setPieceBox(pieceTemplates.getRandomTemplate());
                    }
                } catch (FileNotFoundException e) {
                    this.templatesLoaded = false;
                    response.sendRedirect("get-templates-file");
                    return;
                }
            }
        }    
        
        // Set object as session attributes
        if(session == null){
            session = request.getSession();
            session.setAttribute("board", board);
            session.setAttribute("pieces", pieces);
            session.setAttribute("templates", pieceTemplates);
            session.setAttribute("score", score);
            session.setAttribute("wrongInput", wrongInput);
            session.setAttribute("gameFinished", gameFinished);
            session.setAttribute("scoreboard", scoreboard);
            session.setAttribute("gameHistory", gameHistory);
            session.setAttribute("gameID", gameID);
            session.setAttribute("username", username);
            session.setAttribute("isDatabaseAvailable", isDatabaseAvailable);
        }
        
        // Get values from session
        score = (Integer) session.getAttribute("score");
        wrongInput = (boolean) session.getAttribute("wrongInput");
        gameFinished = (boolean) session.getAttribute("gameFinished");
        
        // Print game state
        try (PrintWriter out = response.getWriter()) {
            viewer.printStart(out);  
            
            if(gameFinished){
                viewer.printGameFinished(out, score);
            }
            else{
                out.println("<div class=\"game-container\">");
                viewer.printBoard(out, board.getBoard());
                out.println("<div class=\"pieces-container\">");
                for(Piece piece:pieces){
                    viewer.printPiece(out, piece.getPieceBox());
                }
                out.println("</div>");
                out.println("</div>");
                
                viewer.printControlPanel(out);
                if(wrongInput){
                    viewer.printWrongInput(out);
                } 
                else{
                    viewer.printBlankSpace(out);
                }
                
                viewer.printScore(out, score);
                
                if(isDatabaseAvailable){
                    viewer.printScoreboardButton(out);
                    viewer.printGameHistoryButton(out);
                }
                else{
                    viewer.printDatabaseUnavailable(out);
                }
                
            }
            
            viewer.printEnd(out);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
