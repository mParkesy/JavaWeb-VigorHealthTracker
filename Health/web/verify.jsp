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
        <link rel="stylesheet" href="css/animate.css">
        <link rel="stylesheet" href="css/master.css">
        <meta charset="UTF-8">
        <title>Vigor - Register</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width = device-width, initial-scale=1">
        <link href="https://use.fontawesome.com/releases/v5.0.7/css/all.css" rel="stylesheet">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js" ></script>
        <script src="https://unpkg.com/sweetalert2@7.18.0/dist/sweetalert2.all.js"></script>



        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

        <link rel="stylesheet" href="css/master.css">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="css/styling.css">
        <script>
            $(document).ready(function () {
                swal({type: 'success',
                    title: 'Success',
                    text: 'Account verified',
                    showConfirmButton: false,
                    timer: 3000
                })
                // Handler for .ready() called.
                window.setTimeout(function () {
                    location.href = "login.jsp";
                }, 3400);
            });
        </script>
    </head>
    <body>
        <div class="row">
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
            <%
                Database db = new Database();
                db.updateVerification(request.getParameter("verification"), 0);

            %>   
        </div>
    </body>
</html>
