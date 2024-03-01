package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.exception.WrongIndexException;

import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.Board;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.GameHistory;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.IndexValidator;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.Piece;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.PieceTemplates;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.Scoreboard;

/**
 * Class used to handle board logic of the game. Updating board, changing pieces etc.
 * 
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.0
 */
@WebServlet(name = "update", urlPatterns = {"/update"})
public class UpdateServlet extends HttpServlet {

    /**
     * Object of the IndexValidator class, used to strictly check user input.
     */
    private IndexValidator indexValidator;
    
    /**
     * Initialization method of update servlet.
     * 
     * @param config servlet configuration
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init (ServletConfig config) throws ServletException {
        super.init(config);
    
        this.indexValidator = new IndexValidator();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get session objects
        HttpSession session = request.getSession();
        Board board = (Board) session.getAttribute("board");
        List<Piece> pieces = (List<Piece>) session.getAttribute("pieces");
        PieceTemplates pieceTemplates = (PieceTemplates) session.getAttribute("templates");
        Integer score = (Integer) session.getAttribute("score");
        Scoreboard scoreboard = (Scoreboard) session.getAttribute("scoreboard");
        GameHistory gameHistory = (GameHistory) session.getAttribute("gameHistory");
        Integer gameID = (Integer) session.getAttribute("gameID");
        String name = (String) session.getAttribute("username");
        boolean isDatabaseAvailable = (boolean) session.getAttribute("isDatabaseAvailable");
        
        // Prepare and validate indexes
        int xIndex = 1, yIndex = 1, pieceIndex = 1;
        try{
            xIndex = Integer.parseInt(request.getParameter("xIndex"));
            yIndex = Integer.parseInt(request.getParameter("yIndex"));
            pieceIndex = Integer.parseInt(request.getParameter("pieceIndex"));
            indexValidator.validIndexes(pieceIndex, xIndex, yIndex);
        }
        catch(NumberFormatException | WrongIndexException e){
            session.setAttribute("wrongInput", true);
            response.sendRedirect("/BlockSudokuWebDB/game");
            return;
        }
        
        // Check if given piece fits on board using given indexes
        if (!indexValidator.pieceFits(xIndex - 1, yIndex - 1, board.getBoard(),
            pieces.get(pieceIndex - 1).getPieceBox())) {
            session.setAttribute("wrongInput", true);
            response.sendRedirect("/BlockSudokuWebDB/game");
            return;
        }
        
        // If it is a new game make a new game in database and get its gameID
        if(board.isEmpty() && isDatabaseAvailable){
            gameID = gameHistory.addNewGame(name);
            session.setAttribute("gameID", gameID);
        }
        
        // Update board if everything was correct
        Piece chosenPiece = pieces.get(pieceIndex - 1);
        board.updateBoard(chosenPiece.getPieceBox(), xIndex - 1 , yIndex - 1);
        
        // Change used piece
        pieces.get(pieceIndex - 1).setPieceBox(pieceTemplates.getRandomTemplate());
        
        // Add score and remove full lines
        score += board.checkFullLines();
        
        // Check if the game is finished
        if (checkGameEnd(board, pieces)) {  
            scoreboard.saveScore(name, score);
            session.setAttribute("gameFinished", true);
            response.sendRedirect("/BlockSudokuWebDB/game");
        }
        
        // Update game in database if it wasnt end of the game
        if(isDatabaseAvailable){
            gameHistory.updateGame(gameID, board, pieces, score);
        }
        
        // Redirect to game servlet
        session.setAttribute("score", score);
        session.setAttribute("wrongInput", false);
        response.sendRedirect("/BlockSudokuWebDB/game");
    }
    
    /**
     * Method used to check if the game is finished. When the player cant fit
     * any more pieces the game is ended. Method checks if any of 3 pieces can
     * fit on any of the indexes of the board.
     *
     * @return boolean value, that says if it is the end of the game.
     */
    private boolean checkGameEnd(Board board, List<Piece> pieces) {
        // Check if user can input any pieces
        for (Piece piece : pieces) {
            for (int xIndex = 0; xIndex < 9; xIndex++) {
                for (int yIndex = 0; yIndex < 9; yIndex++) {
                    if (indexValidator.pieceFits(xIndex, yIndex,
                            board.getBoard(), piece.getPieceBox())) {
                        return false;
                    }
                }
            }
        }

        // If no pieces fit game ends
        return true;
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
