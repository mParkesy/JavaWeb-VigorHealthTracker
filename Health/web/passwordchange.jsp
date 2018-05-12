<%-- 
    Document   : passwordchange
    Created on : 12-May-2018, 22:25:08
    Author     : 100116544
--%>

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
            Database db = new Database();
            db.updateVerification(request.getParameter("verification"), 0);
            
        %>  
    </body>
</html>
