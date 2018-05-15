/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 100116544
 */
@WebServlet(name = "ExerciseController", urlPatterns = {"/ExerciseController"})
public class ExerciseController extends HttpServlet {
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
        // get user ID and date from form
        int userID = Integer.parseInt(request.getParameter("userID"));
        Date date = new Date();
        try {
            // parse date to SQL format
            date = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(request.getParameter("date"));
        } catch (ParseException ex) {
            System.out.println("Failed to parse date in exercise controller");
        }
        // get other parameters
        int activityID = Integer.parseInt(request.getParameter("activityID"));
        int minutes = Integer.parseInt(request.getParameter("minutes"));
        double distance = Double.parseDouble(request.getParameter("distance"));
        String message;
        try {
            // construct exercise object
            Exercise exercise  = new Database().insertExercise(userID, 
                    activityID, date, minutes, distance);
            // create user alert
            message = Database.makeAlert("Exercise added", "success");
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("exercise.jsp")
                                .include(request, response);
        } catch (Exception ex) {
            System.out.println("Failed to construct exercise object");
            message = Database.makeAlert("Failed to add exercise,"
                    + "please try again", "error");
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("exercise.jsp")
                                .include(request, response);
        }
    }

}
