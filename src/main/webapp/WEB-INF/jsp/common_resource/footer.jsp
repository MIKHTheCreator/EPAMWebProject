<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="custom" uri="customtag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="language" value="${not empty language ? language : pageContext.request.locale}" scope="session"/>
<fmt:setBundle basename="locale" var="loc">

<fmt:message bundle="${loc}" key="helloMessage" var="hello">
<fmt:message bundle="${loc}" key="logIn" var="logIn">
<fmt:message bundle="${loc}" key="logOut" var="logOut">
<fmt:message bundle="${loc}" key="registration" var="registration">

<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Header</title>
        <link rel="stylesheet" href="/../../css/header.css">
    </head>

    <body>
            <div class="header">
                <a href="/../main.jsp" class="logo">GloBank</a>
                <c:if test=${currentUser neq null}>
                    <div class="header-right">
                      <custom:helloTag userName=${currentUser.firstName} ${currentUser.secondName}>
                    </div>
                </fmt:message>
                <div class="header-right">
                  <a class="active" href="/../login.jsp">LogIn</a>
                </div>
            </div>

    </body>
</html>