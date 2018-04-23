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
                <h1>Profile Info</h1>
                <ul class="list-group">
                    <li class="list-group-item">Username: ${user.getUsername()}</li>
                    <li class="list-group-item">Email: ${user.getEmail()}</li>
                    <li class="list-group-item">Gender: ${user.getGender()}</li>
                    <li class="list-group-item">Date of birth: ${user.getDob()}</li>
                    <li class="list-group-item">Age: ${user.getAge()}</li>
                    <li class="list-group-item">Postcode: ${user.getPostcode()}</li>
                    <li class="list-group-item">Nationality: ${user.getNationality()}</li>
                </ul>
                <h1 >Health Info</h1>
                <ul class="list-group">
                    <li class="list-group-item">Height: ${user.getHeight()}cm</li>
                    <li class="list-group-item">Weight: ${user.getWeight()}kg</li>
                    <li class="list-group-item">Body Mass Index (BMI): ${user.getBMI()}</li>
                    <li class="list-group-item">Basic Metabolic Rate (BMR): ${user.getBMR()} calories</li>
                    <li class="list-group-item">Harris Benedict Equation:</li>
                    <li class="list-group-item"> - To maintain your weight you need: ${user.getCalories()} Kcal/day</li>
                    <li class="list-group-item"> - To lose 0.5kg per week you need: ${user.getCalories()-500} Kcal/day</li>
                    <li class="list-group-item"> - To lose 1kg per week you need: ${user.getCalories()-1000} Kcal/day</li>
                    <li class="list-group-item"> - To gain 0.5kg per week you need: ${user.getCalories()+500} Kcal/day</li>
                    <li class="list-group-item"> - To gain 1kg per week you need: ${user.getCalories()+1000} Kcal/day</li>

                </ul>
            </div>
            <div class=" col-lg-4 col-md-2 col-sm-1"></div>
        </div>
    </body>
    
</html>
