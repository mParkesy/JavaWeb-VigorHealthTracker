package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import classes.Database;
import classes.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 100116544
 */
@WebServlet(urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {

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
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Database db = new Database();
        try {
            Database.getConnection();
        } catch (Exception ex) {
            System.out.println("Failed to get connection to database "
                    + "before login.");
        }

        String passwordDigest = "";
        try {
            passwordDigest = Database.passwordDigest(password);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Failed to digest entered password on login");
        }

        String error = "";
        try {
            if(db.getUser(username) != null){
                User loginUser = db.getUser(username);
                if(passwordDigest.equals(db.getPassword(loginUser.getID()))){
                    HttpSession session = request.getSession();
                    session.setMaxInactiveInterval(10 * 60);
                    session.setAttribute("user", db.getUser(username));
                    response.sendRedirect("home.jsp");
                } else {
                    error = "Password incorrect for that username";
                    request.setAttribute("message", error);
                    request.getRequestDispatcher("login.jsp")
                            .include(request, response);
                }
            } else {
                error = "The username you entered does not exist";
                request.setAttribute("message", error);
                request.getRequestDispatcher("login.jsp")
                        .include(request, response);
            }
            
        } catch (Exception ex) {
            error = "Error checking credentials, please try again.";
            request.setAttribute("message", error);
            request.getRequestDispatcher("login.jsp")
                    .include(request, response);
        }

    }

}
