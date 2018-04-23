/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author xze15agu
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {


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
            throws ServletException, IOException  {
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
            ex.printStackTrace();
        }
        String postcode = request.getParameter("postcode");
        String nationality = request.getParameter("nationality");
        String email = request.getParameter("email");    
        String h = request.getParameter("height");
        double height = Double.parseDouble(h);
        String w = request.getParameter("weight");
        double weight = Double.parseDouble(w);
        double exercise = Double.parseDouble(request.getParameter("exerciseLevel"));
        
        
        PrintWriter out=response.getWriter();
        
        if(!password.equals(repassword)){
            request.getRequestDispatcher("register.jsp").include(request, response);
            out.print("<h1 style=\"text-align:center\">Entered passwords were not the same.</h1>");
        }
        try{
            String sql = "SELECT COUNT(*) FROM user WHERE username =?";
            PreparedStatement st = Database.getConnection().prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                if(rs.getInt(1) == 0){
                    try {
                        User registerdUser = new User(username, password, firstname,
                                lastname, gender, date, postcode, nationality, email, height,weight,exercise);
                        request.getRequestDispatcher("login.jsp").include(request, response);
                        out.print("<h1 style=\"text-align:center\">Registration successful!</h1>");
                        out.print("<h1 style=\"text-align:center\">Please log in...</h1>");
                    } catch (Exception ex) {
                        request.getRequestDispatcher("register.jsp").include(request, response);
                        out.print("<h1 style=\"text-align:center\">Registration failed</h1>");
                        out.print("<h1 style=\"text-align:center\">Please try again</h1>");
                    }  
                } else { 
                    request.getRequestDispatcher("register.jsp").include(request, response);
                    out.print("<h1 style=\"text-align:center\">Username is already in use. Please chose another.</h1>");
                }
            }
        } catch (Exception ex){
            out.print("<h1 style=\"text-align:center\">Failed to check if username is already in use</h1>");
        }
    }
}
