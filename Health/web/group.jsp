<%-- 
    Document   : group
    Created on : 18-Apr-2018, 12:41:59
    Author     : Parkesy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="classes.*"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
    <head>
        <style>
            body{
                overflow-y: hidden;

            }

            #groupInfo{
                opacity:0;
            }
            #openModal{
                margin-left:20px;
                margin-top:-10px;
            }
            h3{
                display:inline;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="fragments/header.jspf" %>
        <script>
            $(document).ready(function () {

                var groupID = 0;

                $('.nav-link').click(function () {
                    $('.tab-content').show();
                })

                $('.list-group-item a').click(function () {
                    var distance;
                    $('#groupInfo').css('opacity', 1);
                    groupID = $(this).attr('id');
                    $('#groupID').val(groupID);
                    $('li').removeClass('active');
                    $(this).parent().addClass('active');
                    $.get('GroupController', {
                        function: "GroupInfo",
                        groupID: groupID
                    }, function (response) {
                        var obj = JSON.parse(response);
                        $(".card-body .card-title").text(obj.groupName);
                        $(".card-body .card-text").text(obj.description);
                        $('#groupName').val(obj.groupName);
                        $('#description').val(obj.description);
                        $('#distanceGoal').val(obj.distanceGoal);
                        $('#path').val(obj.imagePath);
                        $('body').css('background', "linear-gradient(rgba(255,255,255,0.5), rgba(255,255,255,0.5)),url('" + obj.imagePath + "') no-repeat fixed center");
                        $.get('GroupController', {
                            function: "GroupDistance",
                            groupID: groupID
                        }, function (d) {
                            distance = d;
                            var goal = obj.distanceGoal;
                            var percent = Math.round((distance / goal) * 100);
                            if (percent >= 100) {
                                percent = 100;
                            } else {
                                $('#distance-bar').text(percent + "%");
                            }

                            $('#distance-bar').css('width', percent + '%');
                        })

                        $.get('GroupController', {
                            function: "IsAdmin",
                            groupID: groupID,
                            userID: ${user.getID()}
                        }, function (response) {

                            if (response.trim() == 'true') {
                                $(".admin").show();

                            } else {
                                $(".admin").hide();
                            }
                        })


                        $.get('GroupController', {
                            function: "Members",
                            groupID: groupID,
                            userID: ${user.getID()}
                        }, function (response) {
                            var members = JSON.parse(response);

                            $('.card-body .members').html("");
                            $('.members-title').text("Members - " + members.length);
                            for (i = 0; i < members.length; i++) {
                                $('.card-body .members').append("<li class='list-group-item'>"
                                        + members[i].username + "<a id='" + members[i].id + "' href='#' class='msg-btn'><i class='far fa-comment'></i></a></li>");
                            }
                        })

                        $.get('GroupController', {
                            function: "Leaderboard",
                            groupID: groupID,
                            userID: ${user.getID()}
                        }, function (response) {

                            var members = JSON.parse(response);

                            $('.card-body .leaderboard').html("");

                            for (i = 0; i < members.length; i++) {
                                $('.card-body .leaderboard').append("<li class='list-group-item'><div class='medal'></div>"
                                        + members[i].username + "<span class='distance'>" + members[i].distance + "km</span></li>");
                            }
                        })
                        $.get('GroupController', {
                            function: "LeaderboardCalories",
                            groupID: groupID,
                            userID: ${user.getID()}
                        }, function (response) {

                            var members = JSON.parse(response);

                            $('.card-body .leaderboardC').html("");

                            for (i = 0; i < members.length; i++) {
                                $('.card-body .leaderboardC').append("<li class='list-group-item'><div class='medal'></div>"
                                        + members[i].username + "<span class='distance'>" + members[i].calories + " cal</span></li>");
                            }
                        })
                    }
                    )

                })

                $('#addUser').click(function () {
                    var username = $('#username').val();
                    $.get('GroupController', {
                        function: "AddUser",
                        groupID: groupID,
                        username: username
                    }, function (response) {
                        swal({
                            position: 'top-end',
                            type: 'success',
                            title: 'Invite sent',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    })

                })


            });
        </script>
        <style>
            .admin {
                display: none;
            }
        </style>
    </head>
    <body>
        <%@ include file="fragments/navbar.jspf" %>

        <div class="modal fade" id="modal1" role="dialog">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>Create a group</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body modal-form">
                        <form method ="post" action="GroupController?function=New" class="modal-form">
                            <input type="hidden" name="userID" value="${user.getID()}">
                            <div class="form-group">
                                <label for="groupname">Group Name:</label>
                                <input type="text" class="form-control"  name="name">
                            </div>
                            <div class="form-group">
                                <label for="groupdescription">Group Description:</label>
                                <textarea maxlength="535" class="form-control"  name="description"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="groupdescription">Group Distance Goal(km):</label>
                                <input type="number" step="0" text class="form-control"  name="distanceGoal">
                            </div>
                            <div class="form-group">
                                <label for="grouptheme">Theme:</label>
                                <select class="form-control" name="image">
                                    <option value="img/urban.jpg" >Urban</option>
                                    <option value="img/leaves.jpg" >Nature</option>
                                    <option value="img/runner.jpg" >Running</option>
                                    <option value="img/waves.jpg">Waves</option>
                                </select>
                            </div>

                            <button type='submit' class='btn btn-default'>Create Group</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modal3" role="dialog">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>Update group</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body modal-form">
                        <form method ="post" action="GroupController?function=Update" class="modal-form">
                            <input type="hidden" name="userID" value="${user.getID()}">
                            <input type="hidden" id="groupID" name="groupID" value="">
                            <div class="form-group">
                                <label for="groupname">Change Group Name:</label>
                                <input type="text" class="form-control" id="groupName" name="name">
                            </div>
                            <div class="form-group">
                                <label for="groupdescription">Change Group Description:</label>
                                <textarea maxlength="535" class="form-control" id="description" name="description"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="distanceGoal">Change Group Distance Goal(km):</label>
                                <input type="number" step="0" text class="form-control" id="distanceGoal" name="distanceGoal">
                            </div>
                            <div class="form-group">
                                <label for="grouptheme">Change Theme:</label>
                                <select class="form-control" id="path" name="image">
                                    <option value="img/urban.jpg" >Urban</option>
                                    <option value="img/leaves.jpg" >Nature</option>
                                    <option value="img/runner.jpg" >Running</option>
                                    <option value="img/waves.jpg">Waves</option>
                                </select>
                            </div>

                            <button type='submit' class='btn btn-default'>Update Group</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="row group-container">
            <div class=" col-lg-2 col-md-2 col-sm-1"></div>
            <div class="col-lg-4 col-md-8 col-sm-10 card" id="groupInfo">
                <div class="card-body">
                    <h3 class='card-title'></h3> 
                    <button id="openModal" type="button" class="btn btn-primary admin"
                            data-toggle="modal" data-target="#modal2">
                        Invite
                    </button>
                    <button id="openModal" type="button" class="btn btn-primary admin"
                            data-toggle="modal" data-target="#modal3">
                        Update group
                    </button>
                    <br>
                    <p class="card-text"></p>
                    <div id="goal">
                        <h5 class='goal-title'>Current Distance Goal</h5>
                        <p></p>
                        <div class="progress">
                            <div id="distance-bar" class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar"  ></div>
                        </div>
                    </div>
                    <br>
                    <ul class="nav nav-tabs" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link members-title" href="#members" role="tab" data-toggle="tab"></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#leaderboard" role="tab" data-toggle="tab">Distance Leaderboard</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#leaderboardC" role="tab" data-toggle="tab">Calorie Leaderboard</a>
                        </li>
                    </ul>
                    <div class="tab-content ">
                        <div role="tabpanel" class="tab-pane fade" id="members">
                            <h5 >Member List</h5>
                            <ul class="list-group members "></ul>
                        </div>
                        <div role="tabpanel" class="tab-pane fade" id="leaderboard">
                            <h5 >Weekly Distance Leaderboard</h5>
                            <ul class="list-group leaderboard "></ul>
                        </div>
                        <div role="tabpanel" class="tab-pane fade" id="leaderboardC">
                            <h5 >Weekly Calories Burnt Leaderboard</h5>
                            <ul class="list-group leaderboardC"></ul>
                        </div>
                        <div role="tabpanel" class="tab-pane fade" id="references">ccc</div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="modal2" role="dialog">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4>Add a member</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>
                        <div class="modal-body modal-form">

                            <div class="form-group">
                                <label for="username">Username:</label>
                                <input id="username" type="text" class="form-control"  name="username">
                            </div>
                            <button type="button" class="btn btn-default" data-dismiss='modal' id="addUser" t>Add</button>

                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-8 col-sm-10 groups ">
                <h1>Your Groups</h1>              
                ${message}
                <ul class='list-group'>
                    <c:forEach items="<%=db.getGroupList(currentUser.getID(), 1)%>" var="w">
                        <li class='list-group-item'>
                            <a href="#" id="${w.getGroupID()}"> ${w.getGroupName()}</a>
                        </li>
                    </c:forEach>
                </ul>
                <button type="button" class="btn btn-info btn-lg"
                        data-toggle="modal" data-target="#modal1">
                    Create Group
                </button>
            </div>
            <div class=" col-lg-2 col-md-2 col-sm-1"></div>
        </div>
    </body>
</html>
