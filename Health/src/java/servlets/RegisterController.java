/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.*;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author xze15agu
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/RegisterController"})
public class RegisterController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific alertMessage occurs
     * @throws IOException if an I/O alertMessage occurs
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
     * @throws ServletException if a servlet-specific alertMessage occurs
     * @throws IOException if an I/O alertMessage occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Database db = new Database();
        String alertMessage;
        try {
            Database.getConnection();
        } catch (Exception ex) {
            alertMessage = Database.makeAlert("Vigor is unavailable at "
                    + "this time, please try again later", "error");
            request.setAttribute("message", alertMessage);
            request.getRequestDispatcher("register.jsp")
                    .include(request, response);
            System.out.println("Failed to get connection to database "
                    + "before login.");
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        } catch (ParseException ex) {
            System.out.println("Failed to change date format in "
                    + "register controller");
        }
        String email = request.getParameter("email");
        double height = Double.parseDouble(request.getParameter("height"));
        if (height < 50 && height > 250) {
            alertMessage = Database.makeAlert("The height you entered was "
                    + "either too small or too large, please "
                    + "try again", "error");
            request.setAttribute("error", alertMessage);
            request.getRequestDispatcher("register.jsp")
                    .include(request, response);
        } else {
            double weight = Double.parseDouble(request.getParameter("weight"));
            double exercise
                    = Double.parseDouble(request.getParameter("exerciseLevel"));

            try {
                if (!password.equals(repassword)) {
                    alertMessage = Database.makeAlert("Entered passwords were not "
                            + "the same.", "error");
                    request.setAttribute("error", alertMessage);
                    request.getRequestDispatcher("register.jsp")
                            .include(request, response);
                } else if (db.getUser(username) == null) {
                    InetAddress address = InetAddress.getLocalHost();
                    String stamp = String.valueOf(System.currentTimeMillis());
                    String link = address.getHostAddress()
                            + ":8080/Health/verify.jsp?verification=" + stamp;
                    String message = "Thank you for registering on Vigor Health."
                            + "<br>If you did not register with us then please "
                            + "ignore this email, otherwise please verify your "
                            + "account by following the link below:";
                    EmailSetup verifyEmail = new EmailSetup(email, message,
                            "Vigor Account Activation", link, firstname,
                            "Registration Successful");
                    verifyEmail.setUpEmail();
                    verifyEmail.sendEmail();

                    User registered = db.insertUser(username, password, firstname,
                            lastname, gender, date, email,
                            height, weight, exercise, stamp);
                    alertMessage = Database.makeAlert("Registration successful, "
                            + "please check your emails to verify your account",
                             "success");
                    request.setAttribute("message", alertMessage);
                    request.getRequestDispatcher("login.jsp")
                            .include(request, response);
                } else {
                    alertMessage = Database.makeAlert("Username already exists, "
                            + "please try again", "error");
                    request.setAttribute("error", alertMessage);
                    request.getRequestDispatcher("register.jsp")
                            .include(request, response);
                }
            } catch (Exception ex) {
                System.out.println("Failed to check if username "
                        + "exists during registration");
            }
        }
    }
}
