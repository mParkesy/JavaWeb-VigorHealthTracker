<%-- 
    Document   : goal
    Created on : 03-May-2018, 15:15:46
    Author     : 100116544
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <%@ include file="fragments/header.jspf" %>
         <style>
             body{
                 background:linear-gradient(rgba(255,255,255,0.5), rgba(255,255,255,0.5)),url('img/runner.jpg') no-repeat fixed center;
             }
         </style>
    </head>
    <body >
         <%@ include file="fragments/navbar.jspf" %>
        
        <div class="row">
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
            <div class="col-lg-6 col-md-8 col-sm-10">
                <h1>Goals  <a href="#"><i class="fas fa-plus-circle"></i></a></h1>
                <br>
                <div class="card card-body goal animated fadeIn">
                    <h3 class="card-title">Weight Goal</h3>
                    
                    <p>Your last recorded weight was <b>xxkg</b></p>
                    <div class="progress">
                        <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" style="width: 25%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <br>
                    <p><b>Keep going!</b> You are 25% of the way to your goal</p>
                </div>
                
                <div class="card card-body goal animated fadeIn">
                    <h3 class="card-title">Distance Goal</h3>
                    <p>Your highest recorded distance doing excercises was <b>xx.xkm</b></p>
                    <div class="progress">
                        <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" style="width: 25%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                    <br>
                    <p><b>Keep going!</b> You are 25% of the way to your goal</p>
                </div>
                
            </div>
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
        </div>
    </body>
</html>
