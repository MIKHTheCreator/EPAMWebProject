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
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
        <style>
            <%@include file="/WEB-INF/css/common_footer.css"%>
        </style>
    </head>

    <body>
        <footer>
            <div class="container">
                <div class="row">
                    <div class="col-lg-5 col-md-5 col-sm-4 col-xs-12">
                        <ul class="adress">
                            <span>${adress}</span>
                            <li>
                                <p>Minsk, Republic of Belarus</p>
                            </li>

                            <li>
                                <p>+375 25 733 67 13</p>
                            </li>

                            <li>
                                <p>kharevich.ifconfig@gmail.com</p>
                            </li>
                            <li>
                                <p>MIP © 2021</p>
                            </li>
                        </ul>
                    </div>

                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                        <ul class="contact">
                            <span>${language}</span>
                            <li>
                                <a href="${pageContext.request.contextPath}/bank?command=change_language_command&language=en">English</a>
                            </li>

                            <li>
                                <a href="${pageContext.request.contextPath}/bank?command=change_language_command&language=ru">Russian</a>
                            </li>
                        </ul>
                    </div>

                    <div class="col-lg-3 col-md-3 col-sm-4 col-xs-12">
                        <ul class="social">
                            <span>${social}</span>
                            <li>
                                <a href="https://facebook.com"><i class="fa fa-facebook fa-2x"></i></a>
                            </li>

                            <li>
                                <a href="https://github.com/MIKHTheCreator"><i class="fa fa-github fa-2x"></i></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>
    </body>
</html>