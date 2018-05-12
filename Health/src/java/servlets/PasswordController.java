/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
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
 * @author 100116544
 */
@WebServlet(name = "PasswordController", urlPatterns = {"/PasswordController"})
public class PasswordController extends HttpServlet {

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
        String email = request.getParameter("email");
        String username = request.getParameter("username");

        Database db = new Database();

        try {
            String error;
            User user = db.getUser(username);
            if (user != null) {
                if (email.equals(user.getEmail())) {
                    String stamp = String.valueOf(System.currentTimeMillis());
                    db.updateVerification(stamp, user.getID());
                    InetAddress address = InetAddress.getLocalHost();
                    String ip = address.getHostAddress();
                    String link = ip + ":8080/Health/passwordchange"
                            + ".jsp?verification=" + stamp;
                    EmailSetup emailSetup = new EmailSetup(email, link, "Password change");
                    emailSetup.sendEmail();
                } else {
                    error = Database.makeAlert("Incorrect email address for "
                            + "that username");
                    request.setAttribute("message", error);
                    request.getRequestDispatcher("login.jsp")
                            .include(request, response);
                }
            } else {
                error = Database.makeAlert("That username "
                        + "doesn't exist, try again");
                request.setAttribute("message", error);
                request.getRequestDispatcher("login.jsp")
                        .include(request, response);
            }

        } catch (SQLException ex) {
            System.out.println("Failed to construct user on "
                    + "forgotten password");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to update verification string for user");
        }

    }

}
