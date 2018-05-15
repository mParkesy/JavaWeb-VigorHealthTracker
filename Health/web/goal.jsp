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
         
         <script>
             $(document).ready(function(){
                 $.get('GoalController', {
                        type: "weight",
                        userID: ${user.getID()}
                    }, function (response) {
                        var current = ${user.getWeight()}
                        var obj = JSON.parse(response);
                        if(obj.target > 0){
                            $('.weight').show();
                            $('#weight-select').attr('disabled',true);
                        }
                        var diff = obj.start - obj.target;
                        var percent = Math.round((obj.start-current)/diff*100);

                        if(percent >= 100){
                            percent = 100;
                            $('#weight-text').html('<b>Congratulations!</b> You have reached your goal of ' + obj.target + 'kg');

                        }
                        else{
                            $('#weight-percent').html(percent);
                        }

                        $('#weight-bar').css('width', percent + '%');
                    })
                        
                    
                    $.get('GoalController', {
                        type: "distance",
                        userID: ${user.getID()}
                    }, function (response) {
                        var current = ${user.getExercise()}
                        var obj = JSON.parse(response);
                        if(obj.target > 0){
                            $('.distance').show();
                            $('#distance-select').attr('disabled',true);
                        }
                        var diff = obj.start - obj.target;
                        var percent = Math.round((obj.start-current)/diff*100);
                        if(percent >= 100){
                            percent = 100;
                            $('#distance-text').html('<b>Congratulations!</b>You have reached your goal of ' + obj.target + 'km');
                        }
                        else{
                             $('#distance-percent').html(percent);
                        }

                        $('#distance-bar').css('width', percent + '%');
                        }   
                        
                    )
                    
                    $('#weight, #distance').hide();
                    $('select').change(function(){
                        $('#default').remove();
                        $('#weight, #distance').hide();
                        if($(this).val() == "weight"){
                            $('#weight').show();
                        }
                        else{
                            $('#distance').show();
                        }   
                    })
             })
        </script>
      
    </head>
    <body >
         <%@ include file="fragments/navbar.jspf" %>
        <div class="modal fade" id="addGoal" role="dialog">
          <div class="modal-dialog modal-md">
            <div class="modal-content">
              <div class="modal-header">
                <h4>Add Goal</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
              </div>
              <div class="modal-body modal-form">
                  
                <form class="modal-form" method ="post" action="GoalController">
                    <input type="hidden" name="userID" value="${user.getID()}">
                        <div class="form-group">
                            <label for="date">Type</label>
                            <select name="type">
                                <option id="default" value="" >Select Goal Type</option>
                                <option id="weight-select" value="weight">Weight</option>
                                <option id="distance-select" value="distance">Distance</option>
                            </select>
                        </div>
                        <div id="weight" class="form-group">
                            <label for="weight">Goal Weight</label>
                            <input type="number" class="form-control" step="0.1" name="weight">
                        </div>
                        <div id="distance" class="form-group">
                            <label for="distance">Goal Distance</label>
                            <input type="number" class="form-control" step="0.1" name="distance">
                        </div>        
                        <button type="submit" class="btn btn-default">Add Goal</button>
                </form>    
              </div>

            </div>
          </div>
        </div>
        <div class="row">
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
            <div class="col-lg-6 col-md-8 col-sm-10">
                <h1>Goals  <a data-toggle="modal" data-target="#addGoal" href="#"><i class="fas fa-plus-circle"></i></a></h1>
                <br>
                <div class="card card-body goal weight animated fadeIn" style='display:none'>
                    <h3 class="card-title">Weight Goal</h3>
                    
                    <p>Your last recorded weight was <b>${user.getWeight()}kg</b></p>
                    <div class="progress">
                        <div id="weight-bar" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar"  ></div>
                    </div>
                    <br>
                    <p id="weight-text"><b>Keep going!</b> You are <span id="weight-percent"></span>% of the way to your goal of
                                       <%=db.getGoal(currentUser.getID(),"weight").getTarget()%>
                                       kg</p>
                </div>
                
                <div class="card card-body goal distance animated fadeIn" style='display:none'>
                     
                     
                    <h3 class="card-title">Distance Goal</h3>
                    <p>Your highest recorded distance doing excercises was <b>${user.getExercise()}km</b></p>
                    <div class="progress">
                        <div id="distance-bar" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" ></div>
                    </div>
                    <br>
                    <p id="distance-text"><b>Keep going!</b> You are <span id="distance-percent"></span>% of the way to your goal of 
                    <%=db.getGoal(currentUser.getID(),"distance").getTarget()%>
                                       km</p>
                </div>
                
            </div>
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
        </div>
    </body>
</html>
