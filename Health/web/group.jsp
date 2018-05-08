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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ include file="fragments/header.jspf" %>
        <script>
            $(document).ready(function () {
                var groupID = 0;

                $('td .btn').click(function () {
                    groupID = $(this).attr('id');
                    $.get('GroupController', {
                        function: "AcceptInvite",
                        groupID: groupID,
                        userID: ${user.getID()}
                    }, function (response) {
                        swal(
                            'Invitation Accepted',
                            "user " + ${user.getID()} + " added to Group " + groupID,
                            'success'
                            )
                })
                })
                $('td a').click(function () {
                    groupID = $(this).attr('id');
                    $.get('GroupController', {
                        function: "GroupInfo",
                        groupID: groupID
                    }, function (response) {
                        var obj = JSON.parse(response);
                        alert(obj.description);
                        $(".card-body h5").remove();
                        $(".card-body p").remove();
                        $(".card-body").prepend(response);
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

        <!-- Modal -->
        <div class="modal fade" id="modal1" role="dialog">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>Create a group</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body modal-form">
                        <form method ="post" action="GroupController" class="modal-form">
                            <input type="hidden" name="userID" value="${user.getID()}">
                            <div class="form-group">
                                <label for="groupname">Group Name:</label>
                                <input type="text" class="form-control"  name="name">
                            </div>
                            <div class="form-group">
                                <label for="groupdescription">Group Description:</label>
                                <textarea maxlength="535" class="form-control"  name="description"></textarea>
                            </div>
                            <button type="submit" class="btn btn-default">Create new group</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class=" col-lg-2 col-md-2 col-sm-1"></div>
            <div class="col-lg-4 col-md-8 col-sm-10 card" style="width: 18rem;">
                <div class="card-body">
                    <button id="openModal" type="button" class="btn btn-primary admin"
                            data-toggle="modal" data-target="#modal2">Add new member
                    </button>
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
                            <form class="modal-form">
                                <div class="form-group">
                                    <label for="username">Username:</label>
                                    <input id="username" type="text" class="form-control"  name="username">
                                </div>
                                <button type="submit" class="btn btn-default" id="addUser" t>Add</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 col-md-8 col-sm-10">
                <h1>Groups you have been invited to</h1>                
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Groups you have been invited to</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="<%=db.getGroupList(currentUser.getID(), 0)%>" var="w">
                            <tr>
                                <td>
                                    ${w.getGroupName()}
                                </td>
                                <td>
                                    <button type="button" class="btn btn-default" id="${w.getGroupID()}">
                                        Accept
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button type="button" class="btn btn-info btn-lg"
                        data-toggle="modal" data-target="#modal1">Create Group
                </button>
                <h1>Groups you have joined</h1>                
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Group Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="<%=db.getGroupList(currentUser.getID(), 1)%>" var="w">
                            <tr>
                                <td>
                                    <a href="#" id="${w.getGroupID()}"> ${w.getGroupName()}</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class=" col-lg-2 col-md-2 col-sm-1"></div>
        </div>
    </body>
</html>
