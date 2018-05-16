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
            function generatePie() {
                var protein = 0;
                var fat = 0;
                var carbs = 0;
                var id = 0;
                var name = "";
                $(".protein").each(function () {
                    protein += parseFloat($(this).text());
                });
                $(".fat").each(function () {
                    fat += parseFloat($(this).text());
                });
                $(".carbs").each(function () {
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
            $(document).ready(function () {
                $('#food2').on('input', function (event) {
                    $.get('AutoCompleteController', {
                        food: $('#food2').val()
                    }, function (responseText) {
                        $('#foodList').html(responseText);

                    });
                });
                function getFoodLogs() {
                    //gets old food logs
                     $.get('FoodController', {
                        userID: ${user.getID()},
                        function : "old"
                    }, function (response) {
                        var food = JSON.parse(response);
                        var old = $('#oldLog tbody');
                        old.html("");
                        for (i = 0; i < food.length; i++) {
                            var html = "";
                            console.log(food[i].name);
                            html += "<tr id='" + food[i].id + "'>";
                            html += "<td>" + food[i].name + "</td>";
                            html += "<td >" + food[i].protein + "</td>";
                            html += "<td >" + food[i].carbs + "</td>";
                            html += "<td " + food[i].fat + "</td>";
                            html += "<td> \
                                    <button type='button' class='btn btn-danger add-small'> \
                                     <i class='fas fa-minus'></i></button></td>";
                            html += "</tr>";
                            old.append(html);
                            
                        }
                    });
                    //gets todays food
                    $.get('FoodController', {
                        userID: ${user.getID()},
                        function : "today"
                    }, function (response) {

                        var food = JSON.parse(response);

                        var breakfast = $('#breakfastLog tbody');
                        var snacks = $('#snacksLog tbody');
                        var lunch = $('#lunchLog tbody');
                        var dinner = $('#dinnerLog tbody');

                        $('tbody').html("");
                        for (i = 0; i < food.length; i++) {
                            var html = "";
                            console.log(food[i].name);
                            html += "<tr id='" + food[i].id + "'>";
                            html += "<td>" + food[i].name + "</td>";
                            html += "<td class='protein'>" + food[i].protein + "</td>";
                            html += "<td class='carbs'>" + food[i].carbs + "</td>";
                            html += "<td class='fat'>" + food[i].fat + "</td>";
                            html += "<td> \
                                    <button type='button' class='btn btn-danger add-small'> \
                                     <i class='fas fa-minus'></i></button></td>";

                            html += "</tr>";
                            switch (food[i].meal) {
                                case "breakfast":
                                    breakfast.append(html);
                                    break;
                                case "lunch":
                                    lunch.append(html);
                                    break;
                                case "dinner":
                                    dinner.append(html);
                                    break;
                                case "snacks":
                                    snacks.append(html);
                                    break;
                                default:
                                    break;
                            }

                        }
                        generatePie();
                    });
                }
                getFoodLogs();

                $('body').on('click', '.add-small', function () {
                    id = $(this).attr('id');
                    name = $(this).parent().text();
                    $('#mealModal h4').text("Add - " + name);
                })

                $('body').on('click', '#submitLog', function () {


                    $.post('FoodController', {
                        userID: ${user.getID()},
                        foodID: id,
                        meal: $('#meal').val(),
                        function: "insert"
                    }, function () {
                        getFoodLogs();
                    });

                });

                $('body').on('click', '.btn-danger', function () {


                    $.post('FoodController', {
                        foodLogID: $(this).parent().parent().attr('id'),
                        function: "delete"
                    }, function () {
                        getFoodLogs();
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
        <div class="modal fade" id="mealModal" role="dialog">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>Add Food</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <label>Meal</label>
                        <select class="form-control" id="meal" >
                            <option value="breakfast">Breakfast</option>
                            <option value="lunch">Lunch</option>
                            <option value="dinner">Dinner</option>
                            <option value="snack">Snack</option>
                        </select>

                    </div>
                    <div class="modal-footer">
                        <button type="button" data-dismiss="modal" id="submitLog" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class=" col-lg-2 col-md-2 col-sm-1"></div>
            <div class="col-lg-3 col-md-8 col-sm-10 main">
                <canvas width="400px" height="400px" id="myChart"></canvas>
            </div>
            <div class="col-lg-5 col-md-8 col-sm-10 main">





                <h1 style="display:inline">Today's Food Log</h1>
                <button type="button" class="btn btn-primary add"
                        data-toggle="modal" data-target="#foodModal">
                    <i class="fas fa-plus"></i>
                </button>
                <ul class='list-group' >
                    <li class='list-group-item' data-toggle="collapse" data-target="#breakfast">
                        <h3>Breakfast</h3>
                        <div class='collapse' id='breakfast'>
                            <table class="table" id="breakfastLog">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Protein</th>
                                        <th>Carbs</th>
                                        <th>Fat</th>
                                    </tr>
                                </thead>
                                <tbody> </tbody>
                            </table>
                        </div>
                    </li>    

                    <li class='list-group-item' data-toggle="collapse" data-target="#lunch">
                        <h3>Lunch</h3>
                         <div class='collapse' id='lunch'>
                            <table class="table" id="lunchLog" >
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Protein</th>
                                        <th>Carbs</th>
                                        <th>Fat</th>
                                    </tr>
                                </thead>
                                <tbody> </tbody>
                            </table>
                        </div>
                    </li>

                    <li class='list-group-item' data-toggle="collapse" data-target="#dinner">
                        <h3>Dinner</h3>
                        <div class='collapse' id='dinner'>
                            <table class="table" id="dinnerLog">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Protein</th>
                                        <th>Carbs</th>
                                        <th>Fat</th>
                                    </tr>
                                </thead>
                                <tbody> </tbody>
                            </table>
                        </div>
                    </li>

                    <li class='list-group-item' data-toggle="collapse" data-target="#snacks">
                        <h3>Snacks</h3>
                        <div class='collapse' id='snacks'>
                            <table class="table" id="snacksLog">
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Protein</th>
                                        <th>Carbs</th>
                                        <th>Fat</th>
                                    </tr>
                                </thead>
                                <tbody> </tbody>
                            </table>
                        </div>
                    </li>
                </ul>
                
                <h1>Old Food Logs</h1>
                <li class='list-group-item' data-toggle="collapse" data-target="#old">
                        <h3>Logs</h3>
                         <div class='collapse' id='old'>
                            <table class="table" id="oldLog" >
                                <thead>
                                    <tr>
                                        <th>Name</th>
                                        <th>Protein</th>
                                        <th>Carbs</th>
                                        <th>Fat</th>
                                    </tr>
                                </thead>
                                <tbody> </tbody>
                            </table>
                        </div>
                    </li>

            </div>
            <div class=" col-lg-2 col-md-2 col-sm-1"></div>
        </div>
    </body>
</html>
