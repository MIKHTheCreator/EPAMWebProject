<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="customtag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="helloMessage" var="hello"/>
<fmt:message bundle="${loc}" key="logIn" var="logIn"/>
<fmt:message bundle="${loc}" key="logOut" var="logOut"/>
<fmt:message bundle="${loc}" key="registration" var="registration"/>
<fmt:message bundle="${loc}" key="dearFriend" var="friend"/>
<fmt:message bundle="${loc}" key="userInfo" var="userInfo"/>
<fmt:message bundle="${loc}" key="creditCard" var="creditCard"/>
<fmt:message bundle="${loc}" key="payments" var="payments"/>
<fmt:message bundle="${loc}" key="users" var="users"/>

<%@ page import="com.epam.jwd.dao.entity.user_account.Role" %>

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
                            <a class="passive">
                                <ctg:helloTag/>
                            </a>
                            <c:if test="${sessionScope.currentUser.role eq Role.USER}">
                                <a class="active" href="${pageContext.request.contextPath}/bank?command=show_user_info_page_command">${userInfo}</a>
                                <a class="active" href="${pageContext.request.contextPath}/bank?command=show_payments_page_command">${payments}</a>
                                <a class="active" href="${pageContext.request.contextPath}/bank?command=show_credit_card_page_command">${creditCard}</a>
                            </c:if>
                            <c:if test="${sessionScope.currentUser.role eq Role.ADMIN}">
                                <a class="active" href="${pageContext.request.contextPath}/bank?command=show_users_page_info_page_command">${userInfo}</a>
                                <a class="active" href="${pageContext.request.contextPath}/bank?command=show_users_page_command">${users}</a>
                            </c:if>
                            <a class="active" href="${pageContext.request.contextPath}/bank?command=logout">${logOut}</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="header-right">
                            <a class="passive">
                                <ctg:helloTag/>
                            </a>
                            <a class="active" href="${pageContext.request.contextPath}/bank?command=show_registration_page_command">${registration}</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
    </body>
</html>