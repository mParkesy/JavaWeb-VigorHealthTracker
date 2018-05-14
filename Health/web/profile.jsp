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
            <div class="col-lg-6 col-md-8 col-sm-10 main" style="padding-top: 20px;">
                <div class="card">
                    <img class="card-img-top col-4" src="img/avatar.png" style="margin-top: 15px;">
                    <div class="card-body">
                        <h5 class="card-title">${user.getFirstname()} ${user.getLastname()}</h5>
                    </div>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item">Username: ${user.getUsername()}</li>
                        <li class="list-group-item">Email: ${user.getEmail()}</li>
                        <li class="list-group-item">Gender: ${user.getGender()}</li>
                        <li class="list-group-item">Date of birth: ${user.getDob()}</li>
                        <li class="list-group-item">Age: ${user.getAge()}</li>
                    </ul>
                    <form action="settings.jsp" style="padding-left: 10px; padding-bottom:  10px;">
                        <button type="submit" class="btn btn-info btn-default">Change password</button>
                    </form>
                </div>
                <h1 >Health Info</h1>

                <ul class="list-group">
                    <li class="list-group-item">Height: ${user.getHeight()}cm</li>
                    <li class="list-group-item">Weight: ${user.getWeight()}kg</li>
                    <li class="list-group-item">Body Mass Index (BMI): ${user.getBMI()}</li>
                    <li class="list-group-item">Basic Metabolic Rate (BMR): ${user.getBMR()} calories</li>
                    <li class="list-group-item">
                        <a class="" data-toggle="collapse" href="#collapseList" role="button" aria-expanded="false" aria-controls="collapseList">
                            Calorie Breakdown <i class="far fa-arrow-alt-circle-down"></i>
                        </a>
                    </li>

                    <div class="collapse" id="collapseList">

                        <li class="list-group-item"> - To maintain your weight you need: ${user.getCalories()} calories per day</li>
                        <li class="list-group-item"> - To lose 0.5kg per week you need: ${user.lose500()} calories per day</li>
                        <li class="list-group-item"> - To lose 1kg per week you need: ${user.lose1000()} calories per day</li>
                        <li class="list-group-item"> - To gain 0.5kg per week you need: ${user.gain500()} calories per day</li>
                        <li class="list-group-item"> - To gain 1kg per week you need: ${user.gain1000()} calories per day</li>
                    </div>
                </ul>
            </div>
        </div>
        <div class=" col-lg-3 col-md-2 col-sm-1"></div>
    </div>
</body>

</html>
