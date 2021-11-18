<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="custom" uri="customtag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>

<fmt:setBundle basename="locale" var="loc"/>

<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Footer</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <style>
            <%@include file="/WEB-INF/css/footer.css"%>
        </style>
        <style>
            <%@include file="/WEB-INF/css/footer_buttons.css"%>
        </style>
        <style>
            <%@include file="/WEB-INF/css/language_bar.css"%>
        </style>
    </head>

    <body>
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
                    <div class = "wrapper">
                        <div class="icon facebook">
                            <div class="tooltip">Facebook</div>
                            <span>
                                <a href="https://facebook.com" style="text-decoration: none; color: #282d32">
                                    <i class="fab fa-facebook-f"></i>
                                </a>
                            </span>
                        </div>
                        <div class="icon twitter">
                            <div class="tooltip">Twitter</div>
                            <span>
                                <a href="https://twitter.com" style="text-decoration: none; color: #282d32">
                                    <i class="fab fa-twitter"></i>
                                </a>
                            </span>
                        </div>
                        <div class="icon instagram">
                            <div class="tooltip">Instagram</div>
                            <span>
                                <a href="https://instagram.com" style="text-decoration: none; color: #282d32">
                                    <i class="fab fa-instagram"></i>
                                </a>
                            </span>
                        </div>
                        <div class="icon github">
                            <div class="tooltip">Github</div>
                                <span>
                                    <a href="https://github.com/MIKHTheCreator" style="text-decoration: none; color: #282d32">
                                        <i class="fab fa-github"></i>
                                    </a>
                                </span>
                        </div>
                        <div class="icon youtube">
                            <div class="tooltip">Youtube</div>
                                <span>
                                    <a href="https://youtube.com" style="text-decoration: none; color: #282d32">
                                        <i class="fab fa-youtube"></i>
                                    </a>
                                </span>
                        </div>
                    </div>
                    <div class="dropdown">
                        <c:choose>
                            <c:when test="${sessionScope.language eq 'ru'}">
                                <div class="dropbtn">RU</div>
                            </c:when>
                            <c:otherwise>
                                <div class="dropbtn">EN</div>
                            </c:otherwise>
                        </c:choose>
                        <div class="dropdown-content">
                            <a href="${pageContext.request.contextPath}/bank?command=change_language_command&language=ru">RU</a>
                            <a href="${pageContext.request.contextPath}/bank?command=change_language_command&language=en">EN</a>
                        </div>
                    </div>
                </div>
            </div>
            <p class="copyright">MIP © 2021</p>
        </div>
    </footer>
    </body>
</html>