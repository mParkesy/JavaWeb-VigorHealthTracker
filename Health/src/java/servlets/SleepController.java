/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author 100116544
 */
@WebServlet(name = "SleepController", urlPatterns = {"/SleepController"})
public class SleepController extends HttpServlet {

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
        String bedDate = request.getParameter("bedDate");
        String bedTime = request.getParameter("bedTime");
        String wakeTime = request.getParameter("wakeTime");
        int grade = Integer.parseInt(request.getParameter("sleepGrade"));

        // format date and time together
        String fullBed = bedDate + " " + bedTime;
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        // bed date and time to datetime object
        DateTime bed = fmt.parseDateTime(fullBed);

        String fullWake = bedDate + " " + wakeTime;
        DateTime wake = fmt.parseDateTime(fullWake);

        wake = wake.plusDays(1);
        String message;
        Database db = new Database();
        try {
            Sleep sleep = db.insertSleep(userID, bed, wake, grade);
            message = Database.makeAlert("Sleep added", "success");
            request.setAttribute("message", message);
            request.getRequestDispatcher("sleep.jsp")
                    .include(request, response);

        } catch (Exception ex) {
            System.out.println("Failed to create sleep object");
            message = Database.makeAlert("Failed to add sleep data,"
                    + "please try again", "error");
            request.setAttribute("message", message);
            request.getRequestDispatcher("sleep.jsp")
                    .include(request, response);
        }
    }
}
