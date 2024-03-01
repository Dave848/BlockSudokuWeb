package pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.controller;

import jakarta.servlet.ServletConfig;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.model.Scoreboard;
import pl.polsl.lab4.dawid.jeziorski.blocksudokuweb.view.Viewer;

/**
 * Class responsible for printing on web site scoreboard using Viewer class.
 * 
 * @author Dawid Jeziorski (dj300758@student.polsl.pl)
 * @version 1.0
 */
@WebServlet(name = "scoreboard", urlPatterns = {"/scoreboard"})
public class ScoreboardServlet extends HttpServlet {
    
    /**
     * Object of the Viewer class, used to display different informations for
     * user.
     */
    private Viewer viewer;
    
    /**
     * Initialization function.
     * @param config servlet configuration
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init (ServletConfig config) throws ServletException {
        super.init(config);
        
        this.viewer = new Viewer();
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
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        Scoreboard scoreboard = (Scoreboard) session.getAttribute("scoreboard");
        String[][] records = scoreboard.getSortedRecords();
        
        try (PrintWriter out = response.getWriter()) {
            viewer.printStart(out);
            out.println("<p>Scores from database</p>");
            for(String[] record : records){
                viewer.printRecord(out, record[0], record [1]);
            }
            viewer.printBackButton(out);
            
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
