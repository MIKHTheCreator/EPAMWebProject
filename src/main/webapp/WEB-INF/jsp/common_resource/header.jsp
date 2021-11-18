<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="custom" uri="customtag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="helloMessage" var="hello"/>
<fmt:message bundle="${loc}" key="logIn" var="logIn"/>
<fmt:message bundle="${loc}" key="logOut" var="logOut"/>
<fmt:message bundle="${loc}" key="registration" var="registration"/>
<fmt:message bundle="${loc}" key="dearFriend" var="friend"/>

<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Header</title>
        <style>
            <%@include file="/WEB-INF/css/header.css"%>
        </style>
    </head>

    <body>
            <div class="header">
                <a href="${pageContext.request.contextPath}/bank?command=show_main_page_command" class="logo">GloBank</a>
                <c:choose>
                    <c:when test="${not empty sessionScope.currentUser}">
                        <div class="header-right">
                            <custom:helloTag userName="${sessionScope.currentUser.firstName} ${sessionScope.currentUser.secondName}"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <custom:helloTag userName="${friend}"/>
                    </c:otherwise>
                </c:choose>
                <div class="header-right">
                  <a class="active" href="${pageContext.request.contextPath}/WEB-INF/jsp/login.jsp">${logIn}</a>
                </div>
            </div>
    </body>
</html>