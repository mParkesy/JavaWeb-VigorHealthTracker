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
        int userID = Integer.parseInt(request.getParameter("userID"));
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        int activityID = Integer.parseInt(request.getParameter("activityID"));
        int minutes = Integer.parseInt(request.getParameter("minutes"));
        double distance = Double.parseDouble(request.getParameter("distance"));
        
        PrintWriter out=response.getWriter();
        
        try {
            Exercise exercise  = new Database().insertExercise(userID, activityID, date, minutes, distance);
        } catch (Exception ex) {
            out.print("Error adding exercise");
        }
    }

}
