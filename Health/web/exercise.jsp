<%-- 
    Document   : exercise
    Created on : 04-Apr-2018, 22:12:09
    Author     : 100116544
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="classes.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="fragments/header.jspf" %>

        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
        <script type="text/javascript">
            $(document).ready(function () {
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth() + 1; //January is 0!
                var yyyy = today.getFullYear();
                if (dd < 10) {
                    dd = '0' + dd
                }
                if (mm < 10) {
                    mm = '0' + mm
                }

                today = yyyy + '-' + mm + '-' + dd;
                document.getElementById("datefield").setAttribute("max", today);
            });

        </script>
    </head>
    <body>
        <%@ include file="fragments/navbar.jspf" %>
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>Add to Exercise Log</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body modal-form">    
                        <form class="modal-form" method ="post" action="ExerciseController">
                            <input type="hidden" name="userID" value="${user.getID()}">
                            <div class="form-group">
                                <label for="date">Date:</label>
                                <input type="date" max="" id="datefield" class="form-control" name="date" >
                            </div>
                            <div class="form-group">
                                <label for="activity">Activity:</label>
                                <select name="activityID">
                                    <%                                        for (Activity a : new Database().allActivity()) {
                                            out.println("<option value=" + a.getActivityID() + ">" + a.getActivity() + "</option>");
                                        }

                                    %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="minutes">Minutes Exercised:</label>
                                <input type="number" class="form-control" name="minutes" >
                            </div>
                            <div class="form-group">
                                <label for="distance">Distance Exercised in kilometres:</label>
                                <input type="number" class="form-control" step="0.1" name="distance" >
                            </div>   
                            <button type="submit" class="btn btn-default">Add</button>    
                        </form>
                    </div>
                </div> 
            </div>
        </div>

        <div class="row">    
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
            <div class="col-lg-6 col-md-8 col-sm-10 main">
                <h1>Exercise History</h1>                
                ${message}
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Activity</th>
                            <th>Duration</th>
                            <th>Distance</th>
                            <th>Calories</th>
                            <th>Date</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="<%=db.allExercise(currentUser.getID(), "")%>" var="e">
                            <tr>
                                <td>
                                    ${e.getActivity().getActivity()}
                                </td>
                                <td>
                                    ${e.getMinutes()} minutes
                                </td>
                                <td>
                                    ${e.getDistance()}km
                                </td>
                                <td>
                                    ${e.getCaloriesBurnt()}
                                </td>
                                <td>
                                    ${e.getDate()}
                                </td>
                                <td>
                                    <form action="ExerciseController"  class="modal-form">
                                        <input type="hidden" name="exerciseID" value="${e.getExerciseID()}">
                                        <button type="submit" class="btn btn-danger add-small">
                                            <i class="fas fa-minus"></i>
                                        </button>
                                    </form>
                                </td>    
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button type="button" class="btn btn-info btn-lg"data-toggle="modal" data-target="#myModal">
                    Add Exercise
                </button>
            </div>  
            <div class=" col-lg-3 col-md-2 col-sm-1"></div>
        </div>
    </body>

</html>
