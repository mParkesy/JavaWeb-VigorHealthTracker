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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    
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
        throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter out=response.getWriter();

        String dbPassword = "";
        try {
            dbPassword = Database.passwordDigest(dbPassword);
        } catch (NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        
        try {
            String sql = "SELECT userID, COUNT(*) FROM user "
                    + "WHERE username = ? and password = ?";
            PreparedStatement st = Database.getConnection().prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, dbPassword);

            ResultSet result = st.executeQuery();
            if(result == null){
                request.getRequestDispatcher("login.jsp").include(request, response);
                out.print("<h1 style=\"text-align:center\">Wrong username or password</h1>");
            } else {    
                while(result.next()){
                        HttpSession session = request.getSession();
                        session.setMaxInactiveInterval(10*60);
                        session.setAttribute("user", new Database().getUser(username));
                        response.sendRedirect("home.jsp");
                }
            }

        } catch (Exception ex) {
            request.getRequestDispatcher("login.jsp").include(request, response);
            out.print("<h1 style=\"text-align:center\">Wrong username or password</h1>");
        }
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
        

    }


}
