<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="/../scss/login.css">
        <link rel="stylesheet" href="/../css/footer.css">
        <link rel="stylesheet" href="/../css/header.css">
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
    </head>

    <body>
        <div class="header">
            <a href="#default" class="logo">GloBank</a>
            <div class="header-right">
              <a class="active" href="#home">SignIn</a>
            </div>
        </div>

        <div class="wrapper">
            <form class="form-signin">       
              <h2 class="form-signin-heading">Enter Your Data</h2>
              <input type="text" class="form-control" name="username" placeholder="Username or Email Address" required="" autofocus="" />
              <input type="password" class="form-control" name="password" placeholder="Password" required=""/>      
              <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>   
            </form>
          </div>
          <div class="footer-dark" style="position: fixed; bottom:0px; left:0px; width:100%">
            <footer>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6 col-md-3 item">
                            <h3>Services</h3>
                            <ul>
                                <li><a href="#">Web design</a></li>
                                <li><a href="#">Development</a></li>
                                <li><a href="#">Hosting</a></li>
                            </ul>
                        </div>
                        <div class="col-sm-6 col-md-3 item">
                            <h3>About</h3>
                            <ul>
                                <li><a href="#">Company</a></li>
                                <li><a href="#">Team</a></li>
                                <li><a href="#">Careers</a></li>
                            </ul>
                        </div>
                        <div class="col-md-6 item text">
                            <h3>MIP</h3>
                            <p>Make it Perfect</p>
                        </div>
                    </div>
                    <p class="copyright">MIP © 2021</p>
                </div>
            </footer>
        </div>
    </body>
</html>