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
                        <button type="submit" class="btn btn-info btn-default">Settings</button>
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
                    <li class="list-group-item">
                        <a class="" data-toggle="collapse" href="#collapseFormula" role="button" aria-expanded="false" aria-controls="collapseFormula">
                            Formula Breakdown <i class="far fa-arrow-alt-circle-down"></i>
                        </a>
                    </li>
                    <div class="collapse" id="collapseFormula">
                        <li class="list-group-item"> 
                            Basic Metabolic Rate:<br>
                            &nbsp;= 10 x weight(kg) + 6.25 x height(cm) - 5 x age(years)<br>
                            &nbsp;+ 5 for men<br>
                            &nbsp;-161 for women
                        </li>
                        <li class="list-group-item">
                            Body Mass Index:<br>
                            &nbsp;= weight(kg) / height(m) squared
                        </li>
                        <li class="list-group-item">
                            Harris-Benedict Formula:<br>
                            &nbsp;= BMR x activity level<br>
                            &nbsp;&nbsp;1.2 - Little or no exercise<br>
                            &nbsp;&nbsp;1.375 - 1 to 3 times a week<br>
                            &nbsp;&nbsp;1.55 - 3 to 5 times a week<br>
                            &nbsp;&nbsp;1.725 - 6 to 7 times a week<br>
                            &nbsp;&nbsp;1.9 - Physical job or 2 times a day
                        </li>
                    </div>
                </ul>
            </div>
        </div>
        <div class=" col-lg-3 col-md-2 col-sm-1"></div>
    </div>
</body>

</html>
