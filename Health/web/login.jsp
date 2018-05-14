<%-- 
    Document   : login
    Created on : 04-Mar-2018, 22:41:08
    Author     : 100116544
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <link rel="stylesheet" href="css/animate.css">
        <link rel="stylesheet" href="css/master.css">
        <meta charset="UTF-8">
        <title>Vigor - Login</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width = device-width, initial-scale=1">
        <link href="https://use.fontawesome.com/releases/v5.0.7/css/all.css" rel="stylesheet">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.bundle.min.js" ></script>
        <script src="https://unpkg.com/sweetalert2@7.18.0/dist/sweetalert2.all.js"></script>


        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script type="text/javascript">
            function sweet(){
                swal({type : 'error',
                    title: 'Error',
                    text: 'Please follow the link in your emails',"
                    showConfirmButton: false,"
                    timer: 3000"
                })
                window.location.replace("login.jsp");
            }
        </script>    
    </head>
    <body class="animated fadeIn">
        <%@ include file="fragments/navbar.jspf" %>
        <div class="container">
            <div class=" col-lg-3 col-md-4 col-6 page-header">
                <img style="width:200px" src="img/logo4.png">
            </div>
            ${message}
            <div class="row">
                <div class=" col-lg-3 col-md-2 col-sm-0"></div>
                <div class="jumbotron border col-lg-6 col-md-8 col-sm-12">
                    <div class=" col-lg-3 col-md-2 col-sm-0"></div>
                    <form action="LoginController" method="post">
                        <div class="form-group">
                            <label for="text">Username:</label>
                            <input type="text" class="form-control"  name="username" id="username">
                        </div>
                        <div class="form-group">
                            <label for="pwd">Password:</label>
                            <input type="password" class="form-control" name="password" id="password">
                            <a href ="#" data-toggle="modal" 
                               data-target="#modal1">
                                Forgotten password
                            </a>    
                        </div>
                        <button type="submit" class="btn btn-default">Login</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modal1" role="dialog">
            <div class="modal-dialog modal-md">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4>Forgotten password</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body modal-form">
                        <form class="modal-form" action="LoginController">
                            
                            <div class="form-group">
                                <label for="email">Email:</label>
                                <input type="email" class="form-control" name="email">
                            </div>
                            <div class="form-group">
                                <label for="date">Username:</label>
                                <input type="text" class="form-control" name="username" >
                            </div>
                            <button onclick="sweet();" type="submit" class="btn btn-default">Submit</button>
                        </form>  
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
