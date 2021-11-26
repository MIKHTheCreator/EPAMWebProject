<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="bankSystemUser" var="bsnkSystemUser"/>
<fmt:message bundle="${loc}" key="about" var="about"/>
<fmt:message bundle="${loc}" key="editProfile" var="editProfile"/>
<fmt:message bundle="${loc}" key="options" var="options"/>
<fmt:message bundle="${loc}" key="payments" var="payments"/>
<fmt:message bundle="${loc}" key="creditCard" var="creditCard"/>
<fmt:message bundle="${loc}" key="users" var="users"/>
<fmt:message bundle="${loc}" key="name" var="name"/>
<fmt:message bundle="${loc}" key="email" var="email"/>
<fmt:message bundle="${loc}" key="phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${loc}" key="age" var="age"/>
<fmt:message bundle="${loc}" key="gender" var="gender"/>
<fmt:message bundle="${loc}" key="undefined" var="undefined"/>
<fmt:message bundle="${loc}" key="bestUserEver" var="bestUserEver"/>
<fmt:message bundle="${loc}" key="addPassportInfo" var="addPassportInfo"/>

<%@ page import="com.epam.jwd.dao.entity.user_account.Role" %>

<!DOCTYPE html>
<html>
    <head>
        <title>User Info Page</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <style>
            <%@include file="/WEB-INF/css/user_info.css"%>
        </style>

    </head>

    <body>
    <%@include file="common_resource/header.jsp"%>
    <div class="container emp-profile">
        <form method="post">
            <div class="row">
                <div class="col-md-4">
                    <div class="profile-img">
                        <img src="https://images.unsplash.com/photo-1601814933824-fd0b574dd592?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=80" alt=""/>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="profile-head">
                        <h5>
                            ${sessionScope.currentUser.firstName} ${sessionScope.currentUser.secondName}
                        </h5>
                        <h6>
                            ${bsnkSystemUser}
                        </h6>
                        <p class="proile-rating">${bestUserEver} <span>10/10</span></p>
                        <ul class="nav nav-tabs" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" id="home-tab" data-toggle="tab" role="tab" aria-controls="home" aria-selected="true">${about}</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="profile-work">
                        <p>${options}</p>
                        <c:choose>
                            <c:when test="${sessionScope.currentUser.role eq Role.USER}">
                                <a href="${pageContext.request.contextPath}/bank?command=show_payments_page_command">${payments}</a><br/>
                                <a href="${pageContext.request.contextPath}/bank?command=show_credit_card_page_command">${creditCard}</a><br/>
                                <c:if test="${empty sessionScope.currentUser.passportId}">
                                    <a href="${pageContext.request.contextPath}/bank?command=show_edit_passport_page_command" style="color: #46c1f6; margin-top: 80px; font-size: 20px" >${addPassportInfo}</a><br/>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/bank?command=show_users_page_command">${users}</a>
                            </c:otherwise>
                        </c:choose>
                        <a href="${pageContext.request.contextPath}/bank?command=show_edit_user_info_page_command" style="color: #46c1f6; margin-top: 80px; font-size: 20px" >${editProfile}</a><br/>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="tab-content profile-tab" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <div class="row">
                                <div class="col-md-6">
                                    <label>${name}</label>
                                </div>
                                <div class="col-md-6">
                                    <p>${sessionScope.currentUser.firstName} ${sessionScope.currentUser.secondName}</p>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label>${email}</label>
                                </div>
                                <div class="col-md-6">
                                    <c:choose>
                                        <c:when test="${sessionScope.currentClient.email ne null}">
                                            <p>${sessionScope.currentClient.email}</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p>${undefined}</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label>${phoneNumber}</label>
                                </div>
                                <div class="col-md-6">
                                    <c:choose>
                                        <c:when test="${sessionScope.currentUser.phoneNumber ne null}">
                                            <p>+${sessionScope.currentUser.phoneNumber}</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p>${undefined}</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label>${age}</label>
                                </div>
                                <div class="col-md-6">
                                    <c:choose>
                                        <c:when test="${sessionScope.currentUser.age ne null}">
                                            <p>${sessionScope.currentUser.age}</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p>${undefined}</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <label>${gender}</label>
                                </div>
                                <div class="col-md-6">
                                    <c:choose>
                                        <c:when test="${sessionScope.currentUser.age ne null}">
                                            <p>${sessionScope.currentUser.gender}</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p>${undefined}</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <jsp:include page="common_resource/footer.jsp"/>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </body>
</html>