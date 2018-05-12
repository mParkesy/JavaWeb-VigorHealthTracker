<%-- 
    Document   : verify.jsp
    Created on : 11-May-2018, 00:42:35
    Author     : 100116544
--%>

<%@page import="classes.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                // Handler for .ready() called.
                window.setTimeout(function () {
                    location.href = "login.jsp";
                }, 2000);
            });
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Account verified</h1>
        <%
            Database db = new Database();
            db.updateVerification(request.getParameter("verification"));
            
        %>    
    </body>
</html>
