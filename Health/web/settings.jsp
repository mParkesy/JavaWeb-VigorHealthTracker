<%-- 
    Document   : profile
    Created on : 07-Mar-2018, 12:33:25
    Author     : xze15agu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="fragments/header.jspf" %>

    </head>
    <body>
        <%@ include file="fragments/navbar.jspf" %>
        <div class="row">
            <div class=" col-lg-4 col-md-2 col-sm-1"></div>
            <div class="col-lg-4 col-md-8 col-sm-10">
                <h1>Settings</h1>
                <h2>${message}</h2>
                <form class="modal-form" method ="post" action="UserController">
                    <input type="hidden" name="userID" value="${user.getID()}">
                    <div class="form-group">
                        <label for="weight">Old Password:</label>
                        <input type="password" class="form-control" name="oldpassword">
                    </div>
                    <div class="form-group">
                        <label for="date">New Password:</label>
                        <input type="password" class="form-control" name="newpassword" >
                    </div>
                    <div class="form-group">
                        <label for="date">Retype New Password:</label>
                        <input type="password" class="form-control" name="renewpassword" >
                    </div>
                    <button type="submit" class="btn btn-default">Add</button>
                </form>    
            </div>
            <div class=" col-lg-4 col-md-2 col-sm-1"></div>
        </div>
    </body>

</html>

