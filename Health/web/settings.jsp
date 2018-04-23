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
                <form action="UserController" method="post">
                    <div class="form-group">
                        <label for="text">Change Password</label>
                        <input type="password" class="form-control" placeholder="Old Password"  name="username" id="usrOld" required>
                        <input type="password" class="form-control" placeholder="New Password" name="username" id="usrNew" required>
                        <input type="password" class="form-control" placeholder="Re-enter New Password" name="username" id="usrNewCheck" required>

                        <input type="Submit" class="form-control"/>
                    </div>
                </form>
            </div>
            <div class=" col-lg-4 col-md-2 col-sm-1"></div>
        </div>
    </body>
    
</html>

