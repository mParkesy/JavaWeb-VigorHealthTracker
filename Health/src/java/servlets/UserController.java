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
            int userID = Integer.parseInt(request.getParameter("userID"));
            Database db = new Database();
            
        try {
            String message;
            if(db.getPassword(userID).equals(db.passwordDigest(oldpassword)) && newpassword.equals(renewpassword)){
                String digest = db.passwordDigest(newpassword);
                if(db.updatePassword(digest,userID)){
                    message = "Password changed!";
                } else {
                    message = "Failed to change password, please try again";
                }
            } else {
                message = "You typed your old password incorrectly or the new"
                        + "passwords did not match";
            }
            request.setAttribute("message", message);
                    request.getRequestDispatcher("settings.jsp")
                            .include(request, response);
        } catch (SQLException ex) {
            System.out.println("Failed to get users old password in"
                    + "password change");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Failed to digest password in password change");
        }
    }



}
