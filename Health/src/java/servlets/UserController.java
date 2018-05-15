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
        Database db = new Database();
        String type = request.getParameter("type");
        int userID = 0;
        if (type.equals("2")) {
            String username = request.getParameter("username");
            userID = Integer.parseInt(request.getParameter("userID"));
            String message = "";
            try {
                if (db.getUser(username) == null) {
                    if (db.updateUsername(userID, username) == true) {
                        message = Database.makeAlert("Username changed", "success");
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("settings.jsp")
                                .include(request, response);
                    } else {
                        message = Database.makeAlert("Username change failed, "
                                + "please try again", "error");
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("settings.jsp")
                                .include(request, response);
                    }
                } else {
                    message = Database.makeAlert("That username already exists,"
                            + " please try again", "error");
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("settings.jsp")
                            .include(request, response);
                }
            } catch (Exception ex) {
                System.out.println("Failed to check if username is already "
                        + "in use");
            }
        } else if (type.equals("3")) {
            userID = Integer.parseInt(request.getParameter("userID"));
            double height = Double.parseDouble(request.getParameter("height"));
            String message = "";
            try {
                if (db.updateHeight(userID, height) == true) {
                    message = Database.makeAlert("Height changed", "success");
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("settings.jsp")
                            .include(request, response);
                } else {
                    message = Database.makeAlert("Height change failed, "
                            + "please try again", "error");
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("settings.jsp")
                            .include(request, response);
                }
            } catch (Exception ex) {
                System.out.println("Failed to update users height");
            }
        } else {
            String newpassword = request.getParameter("newpassword");
            userID = Integer.parseInt(request.getParameter("userID"));
            String error;
            boolean canChange = false;
            if (type.equals("4")) {
                String oldpassword = request.getParameter("oldpassword");
                String renewpassword = request.getParameter("renewpassword");

                try {
                    if (db.getPassword(userID).equals(Database
                            .passwordDigest(oldpassword))
                            && newpassword.equals(renewpassword)) {
                        canChange = true;
                    }
                } catch (Exception ex) {
                    System.out.println("Failed to get password for the user "
                            + "userID specified");
                    error = Database.makeAlert("Failed to change password, "
                            + "please try again", "error");
                    request.setAttribute("message", error);
                    request.getRequestDispatcher("settings.jsp")
                            .include(request, response);
                }
            } else if (type.equals("5")) {
                if (request.getParameter("userID") == null) {
                    User user = (User) request.getSession(false).getAttribute("change");
                    userID = user.getID();
                    canChange = true;
                } else {
                    error = Database.makeAlert("Failed to change password, "
                            + "please try again", "error");
                    request.setAttribute("message", error);
                    request.getRequestDispatcher("login.jsp")
                            .include(request, response);
                }
            } else {
                error = Database.makeAlert("There was an error", "error");
                request.setAttribute("message", error);
                request.getRequestDispatcher("settings.jsp")
                        .include(request, response);
            }

            try {

                if (canChange) {
                    String digest = Database.passwordDigest(newpassword);
                    if (db.updatePassword(digest, userID)) {
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
            } catch (Exception ex) {
                System.out.println("Failed to digest password");
            }
        }
    }
}
