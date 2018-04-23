<%-- 
    Document   : weight
    Created on : 08-Mar-2018, 12:33:25
    Author     : xze15agu
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="classes.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="fragments/header.jspf" %> 
        
        <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
        <script>
        window.onload = function () {
            var options = {
                    animationEnabled: true,  
                    axisX: {
                            valueFormatString: "DD MMM"
                    },
                    axisY: {
                            suffix: "kg",
                            includeZero: false
                    },
                    data: [{
                            yValueFormatString: "###.##kg",
                            xValueFormatString: "DD MMM,YY",
                            type: "spline",
                            dataPoints: [
                                <c:forEach items="<%=db.allWeight(currentUser.getID()) %>" var="w">
                                    { x: new Date(${w.getYear()},${w.getMonth()},${w.getDay()}), y: ${w.getWeight()}},
                                </c:forEach>
                            ]
                    }]
            };
            $("#chartContainer").CanvasJSChart(options);
        }
        </script>
    </head>
    <body>
        <%@ include file="fragments/navbar.jspf" %>
        <div class="graph" id="chartContainer" style="height: 300px;"></div>
        <script src="scripts/jquery.canvasjs.min.js"></script>
        

        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog">
          <div class="modal-dialog modal-md">
            <div class="modal-content">
              <div class="modal-header">
                <h4>Add to Weight Log</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
              </div>
              <div class="modal-body modal-form">
                  
                <form class="modal-form" method ="post" action="WeightController">
                    
                    <input type="hidden" name="userID" value="${user.getID()}">
                        
                        <div class="form-group">
                            <label for="weight">Weight:</label>
                            <input type="number" class="form-control" step="0.1" name="weight">
                        </div>
                        <div class="form-group">
                            <label for="date">Date:</label>
                            <input type="date" class="form-control" name="date" >
                        </div>
                        <button type="submit" class="btn btn-default">Add</button>
                </form>    
              </div>

            </div>
          </div>
        </div>
        
        <div class=" col-lg-4 col-md-2 col-sm-1"></div>
        <div class="row">
            <div class=" col-lg-4 col-md-2 col-sm-1"></div>
            <div class="col-lg-4 col-md-8 col-sm-10">
                <h1>Weight History</h1>                
                

                <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Weight(kg)</th>
                    <th>Date</th>
                  </tr>
                </thead>
                <tbody>
                    <c:forEach items="<%=db.allWeight(currentUser.getID()) %>" var="w">
                        <tr>
                            <td>
                                ${w.getWeight()}
                            </td>
                            <td>
                                ${w.getDate()}
                            </td>

                        </tr>
                    </c:forEach>
                </tbody>
                </table>
                <button type="button" class="btn btn-info btn-lg"
                        data-toggle="modal" data-target="#myModal">Add Weight</button>
            </div>
            <div class=" col-lg-4 col-md-2 col-sm-1"></div>
        </div>

    </body>
    
</html>

