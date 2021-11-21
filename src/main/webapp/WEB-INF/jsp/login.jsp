<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="logHere" var="logHere"/>
<fmt:message bundle="${loc}" key="dontHaveAccount" var="dontHaveAccount"/>

<!DOCTYPE html>
<html>
    <head>
        <title>LogIn Page</title>
        <style>
            <%@include file="/WEB-INF/css/login.css"%>
        </style>
    </head>

    <body>
    <%@include file="common_resource/header.jsp"%>
    <div class="loginbox">
            <h1>${logHere}</h1>
            <form action="${pageContext.request.contextPath}/bank?command=login" method="post">
                <p>Username</p>
                <input type="text" name="username" placeholder="Username">
                <p>Password</p>
                <input type="Password" name="password" placeholder="Enter Password">
                <input type="submit" name="command" value="login">
                <a href="${pageContext.request.contextPath}/bank?command=show_registration_page_command">${dontHaveAccount}</a>
            </form>
    </div>
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    </body>
</html>