/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.Food;
import classes.FoodLog;
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
 * @author Daniel
 */
@WebServlet(name = "AutoCompleteController", urlPatterns = {"/AutoCompleteController"})
public class AutoCompleteController extends HttpServlet {
private static final long serialVersionUID = 1L;
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String str = request.getParameter("food").trim();
                    
        response.setContentType("text/plain");
        
        try {
            for(Food f : Food.filterFood(str)){
                response.getWriter().println("<li class=\"list-group-item\" "
                        + "id='" + f.getID() + "'>" + f.getName() + "</li>" );
            }
        } catch (Exception ex) {
            Logger.getLogger(FoodController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    

    
