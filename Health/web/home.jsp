<%-- 
    Document   : home
    Created on : 04-Mar-2018, 22:51:40
    Author     : 100116544
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="fragments/header.jspf" %>
        <script type="text/javascript">
            $(document).ready(function () {
                
                //creates food summary
                var nutrients = <%=db.getNutrients(currentUser.getID())%>;
                var caloriesIn = nutrients.energy;
                var caloriesOut = <%=db.getCaloriesBurnt(currentUser.getID())%>;
                var caloriesSum = caloriesIn - caloriesOut;
                var caloriesMax = <%=currentUser.getCalories()%>;
                
                $('#protein').html("<b>Protein: </b>" + nutrients.protein + "g");
                $('#energy').html("<b>Energy: </b>" + nutrients.energy + "kcal");
                $('#fat').html("<b>Fat: </b>" + nutrients.fat + "g");
                $('#carbs').html("<b>Carbohydrates: </b>" + nutrients.carbs + "g");
                
                
                var ctx = document.getElementById("myChart").getContext('2d');
                var myChart = new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: [
                            'Calories Consumed',
                            'Calories Left'
                        ],
                        datasets: [{
                            data: [caloriesSum,caloriesMax-caloriesSum],
                            backgroundColor: [
                                'rgba(153, 255, 230, 0.65)',
                                'rgba(0,0,0, 0.1)'
                            ],
                            borderWidth:[0,0]
                            
                        }]
                    },
                    options: {
                        cutoutPercentage: 80
                    }
                });
                
                
                //creates weight goal summary
                
                $.get('GoalController', {
                        type: "weight",
                        function: "get",
                        userID: ${user.getID()}
                    }, function (response) {
                       
                        var current = ${user.getWeight()};
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

            });
        </script>

    </head>
    <body>
        <%@ include file="fragments/navbar.jspf" %>
        
        <div class="container animated fadeIn">
            
            <div  id="carouselExampleIndicators" class="carousel slide row border" data-ride="carousel">
                <ol class="carousel-indicators">
                  <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                  <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                  
                </ol>
                <div class="carousel-inner">
                  <div class="carousel-item active">
                      
                            <div >                             
                                <h1>Daily Summary</h1>
                            </div>
                      
                            <div class="chart-container">                             
                                <canvas id="myChart"></canvas>
                            </div>
                            <div class="content">
                                <p>
                                    <span id="energy"></span><br>
                                    <span id="fat"></span><br>
                                    <span id="carbs"></span><br>
                                    <span id="protein"></span><br>
                                </p>
                            </div>
                          
                      
                          
                        
                  </div>
                  <div class="carousel-item ">
                        <h1>Weight</h1>
                        <h3>Current Weight - <b>${user.getWeight()}kg</b></h3>
                        <br>
                        <div class="progress">
                            <div id="weight-bar" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar"  ></div>
                        </div>
                        <br>
                        <p id="weight-text"><b>Keep going!</b> You are <span id="weight-percent"></span>% of the way to your goal of
                                       <%=db.getGoal(currentUser.getID(),"weight").getTarget()%>
                                       kg</p>
                  </div>
                  
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                  <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  <span class="sr-only">Next</span>
                </a>
              </div>
            <div class="row row-tile">
                <a class="border col-lg-3 col-md-4 col-6 square" href ="food.jsp"> 
                    <i style="color:#686ea0" class="fas fa-utensils "></i>
                    <h2>Food</h2>                    
                </a> 
                <a class="border col-lg-3 col-md-4 col-6 square"  href ="sleep.jsp">
                    <i style="color:#cc99ff" class="fas fa-bed"></i>
                    <h2>Sleep</h2>
                </a>
                
                <a class="border col-lg-3 col-md-4 col-6 square"  href ="weight.jsp">
                    <i style="color:#99ffe6" class="fas fa-weight"></i>
                    <h2>Weight</h2>
                </a>

                <a class="border col-lg-3 col-md-4 col-6 square" href ="group.jsp">
                    <i style="color:#99d6ff" class="fas fa-users"></i>
                    <h2>Groups</h2>
                </a>
                
                <a class="border col-lg-3 col-md-4 col-6 square"  href ="exercise.jsp">
                    <i style="color:darkorange" class="fas fa-basketball-ball"></i>
                    <h2>Exercise</h2>
                </a>
                <a class="border col-lg-3 col-md-4 col-6 square"  href ="profile.jsp">
                    <i style="color:lightseagreen" class="fas fa-user-circle"></i>
                    <h2>Profile</h2>     
                </a>
                
                
                <a class="border col-lg-3 col-md-4 col-6 square" href ="goal.jsp">
                        <i style="color:greenyellow" class="fas fa-bullseye"></i>
                        <h2>Goals</h2>
                </a>


            
        </div>
    </div>
    </body>
</html>
