package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.Board;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.GameHistory;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.Piece;

/**
 * Servlet responsible for showing user his last games, and gives opportunity to
 * back to that game.
 *
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.0
 */
@WebServlet(name = "load-game", urlPatterns = {"/load-game"})
public class LoadGameServlet extends HttpServlet {

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
        Integer newGameID = Integer.parseInt(request.getParameter("gameID"));
        
        HttpSession session = request.getSession();
        GameHistory gameHistory = (GameHistory) session.getAttribute("gameHistory");
        Board board = (Board) session.getAttribute("board");
        List<Piece> pieces = (List<Piece>) session.getAttribute("pieces");
        
        gameHistory.loadGame(newGameID);
        
        board.setBoard(gameHistory.getBoardBox());
        List<List<List<Integer>>> newPieces = gameHistory.getPieceBoxes() ;
        for(Piece piece : pieces){
            piece.setPieceBox(newPieces.remove(0));
        }
        session.setAttribute("gameID", newGameID);
        session.setAttribute("score", gameHistory.getScore());
        response.sendRedirect("/BlockSudokuWebDB/game");
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
