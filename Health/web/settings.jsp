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
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
            <div class="col-lg-6 col-md-8 col-sm-10 main">
                <h1>Settings</h1> 
                ${message}
                <ul class="list-group">
                    <li class="list-group-item">
                        <a class="" data-toggle="collapse" href="#collapseForm" role="button" aria-expanded="false" aria-controls="collapseForm">
                            Change password <i class="far fa-arrow-alt-circle-down"></i>
                        </a>
                    </li>
                    <li class="list-group-item">
                        <a class="" data-toggle="collapse" href="#collapseUser" role="button" aria-expanded="false" aria-controls="collapseUser">
                            Change username <i class="far fa-arrow-alt-circle-down"></i>
                        </a>
                    </li>
                    <li class="list-group-item">
                        <a class="" data-toggle="collapse" href="#collapseHeight" role="button" aria-expanded="false" aria-controls="collapseHeight">
                            Change height <i class="far fa-arrow-alt-circle-down"></i>
                        </a>
                    </li>
                </ul>
                <div class="collapse" id="collapseForm">
                    <form class="modal-form" method ="post" action="UserController?type=4">
                        <input type="hidden" name="userID" value="${user.getID()}">
                        <div class="form-group">
                            <label for="oldpassword">Old Password:</label>
                            <input type="password" class="form-control" name="oldpassword" required>
                        </div>
                        <div class="form-group">
                            <label for="newpassword">New Password:</label>
                            <input type="password" class="form-control" name="newpassword" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$" required>
                        </div>
                        <div class="form-group">
                            <label for="repassword">Retype New Password:</label>
                            <input type="password" class="form-control" name="renewpassword" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$" required>
                        </div>
                        <button type="submit" class="btn btn-default">Change password</button>
                    </form> 
                </div>
                <div class="collapse" id="collapseUser">
                    <form class="modal-form" method ="post" action="UserController?type=2">
                        <input type="hidden" name="userID" value="${user.getID()}">
                        <div class="form-group">
                            <label for="username">New username:</label>
                            <input type="text" class="form-control" name="username" required>
                        </div>
                        <button type="submit" class="btn btn-default">Change username</button>
                    </form> 
                </div>
                <div class="collapse" id="collapseHeight">
                    <form class="modal-form" method ="post" action="UserController?type=3">
                        <input type="hidden" name="userID" value="${user.getID()}">
                        <div class="form-group">
                            <label for="height">New Height(cm):</label>
                            <input type="number" class="form-control" step="0.1" name="height" required>
                        </div>
                        <button type="submit" class="btn btn-default">Change height</button>
                    </form> 
                </div>

            </div>
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
        </div>
    </body>

</html>

