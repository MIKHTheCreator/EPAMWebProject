<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="custom" uri="customtag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="language" var="language"/>
<fmt:message bundle="${loc}" key="social" var="social"/>
<fmt:message bundle="${loc}" key="adress" var="adress"/>

<!DOCTYPE html>
<html>
    <head>
        <title>Footer</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <style>
            <%@include file="/WEB-INF/css/common_footer.css"%>
        </style>
    </head>

    <body>
    <footer>
        <section id="footer">
            <div class="container">
                <div class="row text-center text-xs-center text-sm-left text-md-left">
                    <div class="col-xs-12 col-sm-4 col-md-4">
                        <h5>${language}</h5>
                        <ul class="list-unstyled quick-links">
                            <li><a href="${pageContext.request.contextPath}/bank?command=change_language_command&language=en"><i class="fa fa-angle-double-right"></i>English</a></li>
                            <li><a href="${pageContext.request.contextPath}/bank?command=change_language_command&language=ru"><i class="fa fa-angle-double-right"></i>Russian</a></li>

                        </ul>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 mt-2 mt-sm-5">
                        <ul class="list-unstyled list-inline social text-center">
                            <li class="list-inline-item"><a href="https://facebook.com"><i class="fa fa-facebook"></i></a></li>
                            <li class="list-inline-item"><a href="https://twitter.com"><i class="fa fa-twitter"></i></a></li>
                            <li class="list-inline-item"><a href="https://instagram.com"><i class="fa fa-instagram"></i></a></li>
                            <li class="list-inline-item"><a href="" target="_blank"><i class="fa fa-envelope"></i></a></li>
                        </ul>
                    </div>
                    <hr>
                </div>
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 mt-2 mt-sm-2 text-center text-white">
                        <p class="h6">© All right Reversed.<a class="text-green ml-2" target="_blank">MIP</a></p>
                    </div>
                    <hr>
                </div>
            </div>
        </section>
    </footer>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </body>
</html>