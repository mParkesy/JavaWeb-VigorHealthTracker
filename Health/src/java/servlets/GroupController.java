/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import classes.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
        TreeMap board = null;
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
                String message;
                try {
                    User u = db.getUser(username);
                    if (db.insertMember(groupID, u.getID())) {
                        message = Database.makeAlert("Member invited to group, ",
                                 "success");
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("group.jsp")
                                .include(request, response);
                    } else {
                        message = Database.makeAlert("Failed to invite member, "
                                + "the username may not exist", "error");
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("group.jsp")
                                .include(request, response);
                    }

                } catch (Exception ex) {
                    System.out.println("Failed to generate user from username "
                            + "in add member");
                    message = Database.makeAlert("Failed to invite member, "
                                + "the username may not exist", "error");
                        request.setAttribute("message", message);
                        request.getRequestDispatcher("group.jsp")
                                .include(request, response);
                }
                break;
            case "IsAdmin":
                userID = Integer.parseInt(request.getParameter("userID"));
                if (db.isAdmin(userID, groupID)) {
                    response.getWriter().println("true");
                } else {
                    response.getWriter().println("false");
                }
                break;
            case "Members":
                response.getWriter().println("[");
                userID = Integer.parseInt(request.getParameter("userID"));
                ArrayList<User> members = db.getMembers(groupID);
                for (User u : members) {
                    //if(u.getID() != userID){
                    response.getWriter().println(u.toJSON());
                    //}

                    if (members.indexOf(u) != (members.size() - 1)) {
                        response.getWriter().println(",");
                    }
                }
                response.getWriter().println("]");
                break;
            case "Leaderboard":
                response.getWriter().println("[");
                try {
                    board = db.getGroupDistanceLeaderboard(groupID);
                } catch (SQLException ex) {
                    System.out.println("Failed to get distance leaderboard");
                }

                int counter = 0;
                Set<Integer> keys = board.descendingKeySet();
                for (Integer key : keys) {
                    counter++;
                    StringBuilder str = new StringBuilder();
                    try {
                        str.append("{");
                        str.append("\"username\": " + "\"" + db.getUser(key).getUsername() + "\",");
                        str.append("\"distance\": " + "\"" + board.get(key) + "\"");
                        str.append("}");
                        response.getWriter().println(str.toString());

                    } catch (SQLException ex) {
                        System.out.println("Failed to construct user when "
                                + "creating JSON string in "
                                + "distance leaderboard");
                    }

                    if (counter == keys.size() - 1) {
                        response.getWriter().println(",");
                    }

                }

                response.getWriter().println("]");
                break;
            case "LeaderboardCalories":
                response.getWriter().println("[");
                try {
                    board = db.getGroupCalorieLeaderboard(groupID);
                } catch (Exception ex) {
                    System.out.println("Failed to get treemap for calorie"
                            + "leaderboard");
                }

                int count = 0;
                Set<Integer> keyset = board.descendingKeySet();
                for (Integer key : keyset) {
                    count++;
                    StringBuilder str = new StringBuilder();
                    try {
                        str.append("{");
                        str.append("\"username\": " + "\"" + db.getUser(key).getUsername() + "\",");
                        str.append("\"calories\": " + "\"" + board.get(key) + "\"");
                        str.append("}");
                        response.getWriter().println(str.toString());

                    } catch (SQLException ex) {
                        System.out.println("Failed to construct user when "
                                + "creating JSON string in "
                                + "calorie leaderboard");
                    }

                    if (count == keyset.size() - 1) {
                        response.getWriter().println(",");
                    }

                }

                response.getWriter().println("]");
                break;
            case "GroupDistance":
                double distance = db.getGroupDistance(groupID, Database.getLastSunday());
                response.getWriter().println(distance);
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
        String function = request.getParameter("function");
        String distanceGoal = request.getParameter("distanceGoal");
        int userID = Integer.parseInt(request.getParameter("userID"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String image = request.getParameter("image");
        Database db = new Database();
        String message;
        switch (function) {
            case "New":
                try {
                    db.insertGroup(userID, name, description, image,
                            distanceGoal);
                    message = Database.makeAlert("Group successfully added, "
                            + "you are admin for the group", "success");
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("group.jsp")
                            .include(request, response);
                } catch (Exception ex) {
                    System.out.println("Failed to add group");
                    message = Database.makeAlert("Failed to add group, "
                            + "please try again", "error");
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("group.jsp")
                            .include(request, response);
                }
                break;
            case "Update":
                int groupID = Integer.parseInt(request.getParameter("groupID"));
                try {
                    db.updateGroup(groupID, name, description, image,
                            distanceGoal);
                    message = Database.makeAlert("Group successfully updated, ",
                             "success");
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("group.jsp")
                            .include(request, response);
                } catch (Exception ex) {
                    System.out.println("Failed to update group");
                    message = Database.makeAlert("Failed to update group, "
                            + "please try again", "error");
                    request.setAttribute("message", message);
                    request.getRequestDispatcher("group.jsp")
                            .include(request, response);
                }
                break;

        }

    }
}
