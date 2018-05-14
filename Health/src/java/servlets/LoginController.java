package servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import classes.Database;
import classes.EmailSetup;
import classes.User;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 100116544
 */
@WebServlet(urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");

        Database db = new Database();

        try {
            String error;
            User user = db.getUser(username);
            if (user != null) {
                if (email.equals(user.getEmail())) {
                    String stamp = String.valueOf(System.currentTimeMillis());
                    db.updateVerification(stamp, user.getID());
                    InetAddress address = InetAddress.getLocalHost();
                    String ip = address.getHostAddress();
                    String link = ip + ":8080/Health/passwordchange"
                            + ".jsp?verification=" + stamp;
                    EmailSetup emailSetup = new EmailSetup(email, link, 
                            "Password change");
                    emailSetup.sendEmail();
                    error = Database.makeAlert("Please check your emails", 
                            "success");
                    request.setAttribute("message", error);
                    request.getRequestDispatcher("login.jsp")
                        .include(request, response);
                } else {
                    error = Database.makeAlert("Incorrect email address for "
                            + "that username", "error");
                    request.setAttribute("message", error);
                    request.getRequestDispatcher("login.jsp")
                            .include(request, response);
                }
            } else {
                error = Database.makeAlert("That username "
                        + "doesn't exist, try again", "error");
                request.setAttribute("message", error);
                request.getRequestDispatcher("login.jsp")
                        .include(request, response);
            }

        } catch (SQLException ex) {
            System.out.println("Failed to construct user on "
                    + "forgotten password");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to update verification string for user");
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Database db = new Database();
        try {
            Database.getConnection();
        } catch (Exception ex) {
            System.out.println("Failed to get connection to database "
                    + "before login.");
        }

        String passwordDigest = "";
        try {
            passwordDigest = Database.passwordDigest(password);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Failed to digest entered password on login");
        }

        String error = "";
        try {
            if (db.getUser(username) != null) {
                User loginUser = db.getUser(username);
                if (passwordDigest.equals(db.getPassword(loginUser.getID()))) {
                    if (db.getVerification(loginUser.getID()).equals("1")) {
                        HttpSession session = request.getSession();
                        session.setMaxInactiveInterval(10 * 60);
                        session.setAttribute("user", db.getUser(username));
                        response.sendRedirect("home.jsp");
                    } else {
                        error = Database.makeAlert("You have not verified "
                                + "your account, please check your emails", 
                                "error");
                        request.setAttribute("message", error);
                        request.getRequestDispatcher("login.jsp")
                                .include(request, response);
                    }
                } else {
                    error = Database.makeAlert("Password incorrect for that "
                            + "username", "error");
                    request.setAttribute("message", error);
                    request.getRequestDispatcher("login.jsp")
                            .include(request, response);
                }
            } else {
                error = Database.makeAlert("The username you entered does not "
                        + "exist", "error");
                request.setAttribute("message", error);
                request.getRequestDispatcher("login.jsp")
                        .include(request, response);
            }

        } catch (Exception ex) {
            error = Database.makeAlert("Error checking credentials, please "
                    + "try again.", "error");
            request.setAttribute("message", error);
            request.getRequestDispatcher("login.jsp")
                    .include(request, response);
            ex.printStackTrace();
        }

    }

}
