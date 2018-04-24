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
            });
        </script>

    </head>
    <body>
        <%@ include file="fragments/navbar.jspf" %>
        <%            if (session.getAttribute("user") == null) {
                response.sendRedirect("login.jsp");
            }

        %>
        <div class="container animated fadeIn">
            <div class=" col-lg-3 col-md-4 col-6 page-header">
                <img src="img/logo.png">
            </div>
            <div class="row row-tile">
                <div class="border col-lg-3 col-md-4 col-6 square ">
                    <a href ="food.jsp">
                        <i class="fas fa-utensils "></i>
                        <h2>Food</h2>   
                    </a>   
                </div>	
                <div class="border col-lg-3 col-md-4 col-6 square">
                    <a href ="sleep.jsp">
                        <i style="color:#cc99ff" class="fas fa-bed"></i>
                        <h2>Sleep</h2>
                    </a>
                </div>
                <div class="border col-lg-3 col-md-4 col-6 square ">
                    <a href ="weight.jsp">
                        <i style="color:#99ffe6" class="fas fa-weight"></i>
                        <h2>Weight</h2>
                    </a>
                </div>
                <div class="border col-lg-3 col-md-4 col-6 square ">
                    <a href ="group.jsp">
                        <i style="color:#99d6ff" class="fas fa-users"></i>
                        <h2>Groups</h2>
                    </a>
                </div>
                <div class="border col-lg-3 col-md-4 col-6 square ">
                    <a href ="exercise.jsp">
                        <i style="color:darkorange" class="fas fa-basketball-ball"></i>
                        <h2>Exercise</h2>
                    </a>
                </div>
                <div class="border col-lg-3 col-md-4 col-6 square ">
                    <a href ="profile.jsp">
                        <i style="color:lightseagreen" class="fas fa-user-circle"></i>
                        <h2>Profile</h2>   
                    </a>   
                </div>
                <div class="border col-lg-3 col-md-4 col-6 square ">
                    <a href ="settings.jsp">
                        <i style="color:darkgrey" class="fas fa-cog "></i>
                        <h2>Settings</h2>   
                    </a>   
                </div>	
            </div>
        </div>

    </body>
</html>
