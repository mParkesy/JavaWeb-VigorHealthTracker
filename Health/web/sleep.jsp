<%-- 
    Document   : sleep
    Created on : 08-Mar-2018, 23:38:34
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
    </head>
    <body>
        <%@ include file="fragments/navbar.jspf" %>
        <div class="row">
            <div class=" col-lg-4 col-md-2 col-sm-1"></div>
            <div class="col-lg-4 col-md-8 col-sm-10">
                <h1>Sleep form</h1>
                <form method ="post" action="SleepController">
                    <input type="hidden" name="userID" value="${user.getID()}">
                    
                        <div class="form-group">
                            <label for="bedtime">Bed Time:</label>
                            <input type="datetime-local" class="form-control"  name="bedTime">
                        </div>
                        <div class="form-group">
                            <label for="bedtime">Wake Time:</label>
                            <input type="datetime-local" class="form-control"  name="wakeTime">
                        </div>
                        <div class="form-group">
                            <label for="bedtime">Sleep Grade (1-10):</label>
                            <input type="number" class="form-control"  min="1" max="10" name="sleepGrade">
                        </div>
                        <button type="submit" class="btn btn-default">Add</button>
                        
                    </form>
                
            </div>
        </div>
        <div class=" col-lg-4 col-md-2 col-sm-1"></div>
        <div class="row">
            <div class=" col-lg-4 col-md-2 col-sm-1"></div>
            <div class="col-lg-4 col-md-8 col-sm-10">
                <h1>Sleep history</h1>
                <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Weight(kg)</th>
                    <th>Date</th>
                  </tr>
                </thead>
                <tbody>
                    <c:forEach items="<%=db.allSleep(currentUser.getID()) %>" var="s">
                        <tr>
                            <td>
                                ${s.getTotalSleep()}
                            </td>

                        </tr>
                    </c:forEach>
                </tbody>
                </table>
            </div>
            <div class=" col-lg-4 col-md-2 col-sm-1"></div>
        </div>

    </body>
    
</html>