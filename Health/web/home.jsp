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
                var tile = "<div class='border col-lg-3 col-md-4col-6 square animated fadeInRight'></div>";
                var icon = "<i class='far fa-question-circle'></i>";
                var title = "<h2>Empty</h2>";

                function createTile() {
                    $(".row").append(
                            $(tile).append(icon)
                            .append(title)
                            );
                }
                $("img").click(function () {
                    createTile();

                });
                $(".square").click(function () {
                    //$(this).fadeOut();
                });
                
                function generatePie(){
                    var protein = 0;
                    var fat = 0;
                    var carbs = 0;
                    
                    var ctx = document.getElementById("dailyCals").getContext('2d');
                    var myChart = new Chart(ctx, {
                        type: 'doughnut',
                        data: {
                            labels: ['Burnt','Left'],
                            datasets: [{
                                data: [2000, 1000],
                                
                                borderWidth: 1,
                                backgroundColor: [
                                    'rgba(75, 192, 192, 0.75)',
                                    'rgba(0, 0, 0, 0.1)'
                                ]     
                            }]
                        },
                        options:  {
                            cutoutPercentage : 90
                        }
                        })
                    };
                
                generatePie();
                
            });
        </script>
        
    </head>
    <body>
        <%@ include file="fragments/navbar.jspf" %>
        <%
            if(session.getAttribute("user") == null){
                response.sendRedirect("login.jsp");
            }
            
        %>
        <div class="container animated fadeIn">
            <div class=" page-header">
                <img src="img/logo.png">
                
            </div>
            
                <div class="ol-lg-4 col-md-4 col-12">
                    <canvas width="300px" height="300px" id="dailyCals"></canvas>
                    
                    
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
                
                <a class="border col-lg-3 col-md-4 col-6 square"  href ="settings.jsp">
                    <i style="color:darkgrey" class="fas fa-cog "></i>
                    <h2>Settings</h2>   
                </a> 
            </div>
        </div>
       
    </body>
</html>
