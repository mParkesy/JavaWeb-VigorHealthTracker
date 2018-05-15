<%-- 
    Document   : food
    Created on : 04-Apr-2018, 23:12:56
    Author     : Daniel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="fragments/header.jspf" %>
        <title>JSP PAGE</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="http://code.jquery.com/jquery-1.10.2.js"
	type="text/javascript"></script>
        
        <script type="text/javascript">
            function generatePie(){
                var protein = 0;
                var fat = 0;
                var carbs = 0;
                $(".protein").each(function(){
                  protein += parseFloat($(this).text());
                });
                $(".fat").each(function(){
                  fat += parseFloat($(this).text());
                });
                $(".carbs").each(function(){
                  carbs += parseFloat($(this).text());
                });

                var ctx = document.getElementById("myChart").getContext('2d');
                var myChart = new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: [
                            'Protein',
                            'Fat',
                            'Carbs'
                        ],
                        datasets: [{
                            data: [protein, fat, carbs],
                            backgroundColor: [
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(255, 159, 64, 0.2)'
                            ]
                        }]
                    }
                });
            }
            $(document).ready(function() {
                $('#food2').on('input',function(event) {
                    $.get('AutoCompleteController', {
                            food : $('#food2').val()
                    }, function(responseText) {
                            $('#foodList').html(responseText);
                            
                    });
                });
                
                $.get('FoodController', {
                    userID : ${user.getID()}
                }, function(responseText) {
                    $('#foodLog tbody').html(responseText);
                    generatePie();
                });
                
                $('body').on('click','li',function(){
                
                    var id = $(this).attr('id');
                    $.post('FoodController', {
                        userID : ${user.getID()},
                        foodID : id,
                        meal: 'lunch'
                    }, function() {
                        $.get('FoodController', {
                            userID : ${user.getID()}
                        }, function(response) {
                            $('#foodLog tbody').html(response);
                            generatePie();
                        });
                    });
                    
                });
            
            });
        </script>
        <style>
            li{
                
            }
        </style>
    </head>
    <body>
        
        <%@ include file="fragments/navbar.jspf" %>
        <div class="modal fade" id="foodModal" role="dialog">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>Add Food</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                          <input placeholder="Search" type="text" id="food2" class="form-control" />
                          
                            <ul class="list-group" id="foodList"></ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class=" col-lg-2 col-md-2 col-sm-1"></div>
            <div class="col-lg-4 col-md-8 col-sm-10 main">
                <canvas width="400px" height="400px" id="myChart"></canvas>
            </div>
            <div class="col-lg-4 col-md-8 col-sm-10 main">
                
            
                 
           
           
            <h1 style="display:inline">Food Log</h1>
            <button type="button" class="btn btn-primary add"
                    data-toggle="modal" data-target="#foodModal">
                <i class="fas fa-plus"></i>
            </button>
            <table class="table" id="foodLog">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Protein</th>
                        <th>Carbs</th>
                        <th>Fat</th>
                    </tr>
                </thead>
                <tbody>
                    
                </tbody>
            </table>
            
            </div>
            <div class=" col-lg-2 col-md-2 col-sm-1"></div>
        </div>
    </body>
</html>
