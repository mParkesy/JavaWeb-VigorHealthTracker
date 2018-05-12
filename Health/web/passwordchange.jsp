<%-- 
    Document   : passwordchange
    Created on : 12-May-2018, 22:25:08
    Author     : 100116544
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="classes.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
            String stamp = request.getParameter("verification");
            String userID = request.getParameter("id");
            
            Database db = new Database();
            if (db.updateVerification(stamp, 0) == true) {
        %>
        <form class="modal-form" method ="post" action="UserController">
            <input type="hidden" name="userID" value="${id}">
            <div class="form-group">
                <label for="oldpassword">Old Password:</label>
                <input type="password" class="form-control" name="oldpassword">
            </div>
            <div class="form-group">
                <label for="newpassword">New Password:</label>
                <input type="password" class="form-control" name="newpassword" >
            </div>
            <div class="form-group">
                <label for="repassword">Retype New Password:</label>
                <input type="password" class="form-control" name="renewpassword" >
            </div>
            <button type="submit" class="btn btn-default">Add</button>
        </form> 
        <%
            } else {
                response.sendRedirect("login.jsp");
            }
        %>


    </body>
</html>
