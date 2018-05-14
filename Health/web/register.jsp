<%-- 
    Document   : login
    Created on : 04-Mar-2018, 22:41:08
    Author     : 100116544
--%>

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
    </head>
    <body class="animated fadeIn">
        <div class="row" style="padding-top: 20px;">
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
            <div class="col-lg-6 col-md-8 col-sm-10 main animated fadeInUp">
                ${error}
                <form action="RegisterController" method="post">
                    <div class="form-group">
                        <label for="text">Username:</label>
                        <input type="text" class="form-control"  name="username" id="username" required>
                    </div>
                    <div class="form-group">
                        <label for="pwd">
                            Password (must contain at least 1 
                            uppercase character, 1 lowercase character, 
                            and 1 number):
                        </label>
                        <input type="password" class="form-control" name="password" id="password" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$" required>
                    </div>
                    <div class="form-group">
                        <label for="pwd">Retype Password:</label>
                        <input type="password" class="form-control" name="repassword" id="repassword" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$" required>
                    </div>
                    <div class="form-group">
                        <label for="text">Firstname:</label>
                        <input type="text" class="form-control"  name="firstname" id="firstname" required>
                    </div>
                    <div class="form-group">
                        <label for="text">Lastname:</label>
                        <input type="text" class="form-control"  name="lastname" id="lastname" required>
                    </div>
                    <div class="form-group">
                        <label for="text">Gender:</label>
                        <input type="radio" class="form-control" name="gender" value="m" required> Male
                        <input type="radio" class="form-control" name="gender" value="f"> Female
                    </div>
                    <div class="form-group">
                        <label for="text">Date of birth:</label>
                        <input type="date" class="form-control" name="dob" id="dob" min="1900-01-01" max="2006-01-01" required>
                    </div>
                    <div class="form-group">
                        <label for="text">Email:</label>
                        <input type="email" class="form-control"  name="email" id="email">
                    </div>
                    <div class="form-group">
                        <label for="text">Height:</label>
                        <input type="number" class="form-control" step="0.1" name="height" id="height" required>
                    </div>
                    <div class="form-group">
                        <label for="text">Weight:</label>
                        <input type="number" class="form-control" step="0.1" name="weight" id="weight">
                    </div>
                    <div class="form-group">
                        <label for="text">How much exercise do you do?</label>
                        <select class="form-control" name="exerciseLevel">
                            <option value="1.2">Little to no exercise</option>
                            <option value="1.375">1-3 times a week</option>
                            <option value="1.55">3-5 times a week</option>
                            <option value="1.725">6-7 times a week</option>
                            <option value="1.9">Physical job or exercise 2 times a day</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-info btn-lg">Register</button>
                </form>
            </div>
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
        </div>
    </body>
</html>
