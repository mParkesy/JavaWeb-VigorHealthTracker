/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Database;
import classes.Group;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author owner
 */
@WebServlet(name = "GoalController", urlPatterns = {"/GoalController"})
public class GoalController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Database db = new Database();
        String message;
        String function = request.getParameter("function");
        String type = request.getParameter("type");
        int userID = Integer.parseInt(request.getParameter("userID"));
        if ("get".equals(function)){
            try {
            response.getWriter().println(db.getGoal(userID, type).toJSON());
            } catch (Exception ex) {
                System.out.println("Failed to get goal for JSON");
            }
        }
        else if (function.equals("delete")){
            try {
                db.deleteGoal(db.getGoal(userID, type).getGoalID());
                
            } catch (Exception ex) {
                Logger.getLogger(GoalController.class.getName()).log(Level.SEVERE, null, ex);
                
            }
            
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
        Database db = new Database();
        String message;
        String type = request.getParameter("type");
        int userID = Integer.parseInt(request.getParameter("userID"));
        String distance = request.getParameter("distance");
        String weight = request.getParameter("weight");
        double target = 0;
        double start = 0;
        //weight clause
        if (distance.equals("")) {
            target = Double.parseDouble(weight);
            try {
                start = db.currentWeight(userID).getWeight();
                message = Database.makeAlert("Goal inserted", "success");
                request.setAttribute("message", message);
                request.getRequestDispatcher("goal.jsp")
                    .include(request, response);
            } catch (Exception ex) {
                System.out.println("Failed to construct weight "
                        + "object for goal insertion");
            }
        //distance clause
        } else {
            target = Double.parseDouble(distance);
            start = db.getMaxExercise(userID).getDistance();
        }
        db.insertGoal(userID, start, target, type);

    }
}
