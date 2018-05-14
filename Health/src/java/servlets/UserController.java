/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {


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
            String oldpassword = request.getParameter("oldpassword");
            String newpassword = request.getParameter("newpassword");
            String renewpassword = request.getParameter("renewpassword");
            int userID = 0;
            if(request.getParameter("userID") == null){
                User user = (User) 
                        request.getSession(false).getAttribute("change");
                userID = user.getID();
            } else {
                userID = Integer.parseInt(request.getParameter("userID"));
            }
            
            Database db = new Database();

        try {
            String error;
            if(db.getPassword(userID).equals(Database.passwordDigest(oldpassword)) && newpassword.equals(renewpassword)){
                String digest = Database.passwordDigest(newpassword);
                if(db.updatePassword(digest,userID)){
                    error = Database.makeAlert("Password change successful",
                            "success");
                    request.setAttribute("message", error);
                    request.getRequestDispatcher("login.jsp")
                        .include(request, response);
                } else {
                    error = Database.makeAlert("Failed to change password, "
                            + "please try again", "error");
                    request.setAttribute("message", error);
                    request.getRequestDispatcher("login.jsp")
                        .include(request, response);
                }
            } else {
                error = Database.makeAlert("You typed your old password "
                        + "incorrectly or the new passwords did not match",
                        "error");
                request.setAttribute("message", error);
                    request.getRequestDispatcher("login.jsp")
                        .include(request, response);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get users old password in"
                    + "password change");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Failed to digest password in password change");
        }
    }



}
