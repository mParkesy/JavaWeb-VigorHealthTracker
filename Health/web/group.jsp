<%-- 
    Document   : group
    Created on : 18-Apr-2018, 12:41:59
    Author     : Parkesy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="fragments/header.jspf" %>

    </head>
    <body>
        <%@ include file="fragments/navbar.jspf" %>
        <form method ="post" action="GroupController">
            <input type="hidden" name="userID" value="${user.getID()}">
            <div class="form-group">
                <label for="groupname">Group Name:</label>
                <input type="text" class="form-control"  name="name">
            </div>
            <button type="submit" class="btn btn-default">Create new group</button>
        </form>

    </body>
</html>
