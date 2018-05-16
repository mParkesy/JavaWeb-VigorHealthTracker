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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author xze15agu
 */
@WebServlet(name = "WeightController", urlPatterns = {"/WeightController"})
public class WeightController extends HttpServlet {

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
        int weightID = Integer.parseInt(request.getParameter("weightID"));
        Database db = new Database();
        db.deleteWeight(weightID);
        String message = Database.makeAlert("Weight removed", "success");
        request.setAttribute("message", message);
        request.getRequestDispatcher("weight.jsp")
                .include(request, response);
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
        double weight = Double.parseDouble(request.getParameter("weight"));
        Date date = new Date();
        String message;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(request.getParameter("date"));
        } catch (ParseException ex) {
            System.out.println("Failed to parse date when constructing weight");
            message = Database.makeAlert("Failed to add weight data,"
                    + "please try again", "error");
            request.setAttribute("message", message);
            request.getRequestDispatcher("weight.jsp")
                    .include(request, response);
        }
        Database db = new Database();
        try {
            db.insertWeight(userID, weight, date);
            message = Database.makeAlert("Weight added", "success");
            request.setAttribute("message", message);
            request.getRequestDispatcher("weight.jsp")
                    .include(request, response);
            Goal g = db.getGoal(userID, "weight");
            if (weight <= g.getTarget()) {
                db.insertNotification(userID, "Weight Goal achieved!");
                //db.deleteGoal(g.getGoalID());
            }

        } catch (Exception ex) {
            System.out.println("Failed to construct weight object");
            message = Database.makeAlert("Failed to add weight data,"
                    + "please try again", "error");
            request.setAttribute("message", message);
            request.getRequestDispatcher("weight.jsp")
                    .include(request, response);
        }
    }

}
