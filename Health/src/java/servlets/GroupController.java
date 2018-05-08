/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Parkesy
 */
@WebServlet(name = "GroupController", urlPatterns = {"/GroupController"})
public class GroupController extends HttpServlet {

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
        String function = request.getParameter("function");
        int groupID = Integer.parseInt(request.getParameter("groupID"));
        int userID = 0;
        Database db = new Database();
        Group g = db.getGroup(groupID);
        switch (function) {
            case "AcceptInvite":
                userID = Integer.parseInt(request.getParameter("userID"));
                db.acceptInvite(groupID, userID);
                break;
            case "GroupInfo":
                response.getWriter().println(g.toJSON());
                break;
            case "AddUser":
                String username = request.getParameter("username");
                try {
                    User u = db.getUser(username);
                    if (db.insertMember(groupID, u.getID())) {
                        response.getWriter().println("<h2>Member added</h2>");
                    } else {
                        response.getWriter().println("<h1>Failed to add member</h1>");
                    }
                    
                } catch (Exception ex) {
                    System.out.println("Failed to generate user from username "
                            + "in add member");
                }   break;
            case "IsAdmin":
                userID = Integer.parseInt(request.getParameter("userID"));
                if(db.isAdmin(userID, groupID)){
                    response.getWriter().println("true");
                } else {
                    response.getWriter().println("false");
                }
                break;
            case "Members":
                response.getWriter().println("[");
                userID = Integer.parseInt(request.getParameter("userID"));
                ArrayList<User> members = db.getMembers(groupID);
                for(User u: members){
                    //if(u.getID() != userID){
                        response.getWriter().println(u.toJSON());
                    //}
                    
                    if(members.indexOf(u) != (members.size() -1)){
                        response.getWriter().println(",");
                    }
                }
                response.getWriter().println("]");
                break;
            default:
                break;
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
        int userID = Integer.parseInt(request.getParameter("userID"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        try {
            new Database().insertGroup(userID, name, description);
            response.sendRedirect("group.jsp");
        } catch (Exception ex) {
            System.out.println("Failed to add group");
        }

    }
}
