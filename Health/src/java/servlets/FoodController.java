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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
@WebServlet(urlPatterns = {"/FoodController"})
public class FoodController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userID = Integer.parseInt(request.getParameter("userID"));
        //String function = request.getParameter("function").trim();
        User user = (User) request.getSession(false).getAttribute("user");

        List<FoodLog> logs = new ArrayList<>();
        try {
            logs = new Database().allFoodLogs(userID);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        //response.setContentType("text/plain");
        response.getWriter().println("[");
        try {
            for (FoodLog f : logs) {
                response.getWriter().println(f.getFood().toJSON());
                if(f != logs.get(logs.size() - 1)){
                    response.getWriter().println(",");
                }
                /*
                Food food = f.getFood();
                response.getWriter().println("<tr>");
                response.getWriter().println("<td>" + food.getName() + "</td>");
                response.getWriter().println("<td class='protein'>" + food.getProtein() + "</td>");
                response.getWriter().println("<td class='carbs'>" + food.getCarbs() + "</td>");
                response.getWriter().println("<td class='fat'>" + food.getFat() + "</td>");
                response.getWriter().println("</tr>");
                */
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Food controller error");
        }
        response.getWriter().println("]");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String meal = request.getParameter("meal").trim();
        int userID = Integer.parseInt(request.getParameter("userID"));
        int foodID = Integer.parseInt(request.getParameter("foodID"));

        Date date = new Date();
        /*
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
         */
        try {
            FoodLog log = new Database().insertFoodLog(foodID, userID, meal, date);
            request.getRequestDispatcher("food.jsp").include(request, response);
        } catch (Exception ex) {
            request.getRequestDispatcher("home.jsp").include(request, response);
        }

    }

}
