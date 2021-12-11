<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="allUsers" var="allUsers"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="secondName" var="secondName"/>
<fmt:message bundle="${loc}" key="phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${loc}" key="age" var="age"/>
<fmt:message bundle="${loc}" key="gender" var="gender"/>

<!DOCTYPE html>
<html>
<head>
    <title>Users Page</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-csss">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel='stylesheet' type='text/css'>
    <style>
        <%@include file="/WEB-INF/css/payments.css"%>
    </style>
</head>

<%@include file="common_resource/header.jsp"%>
<body style="background-color: #00c6ff">
<div class="container">
    <div class="row">
        <p></p>

        <div class="col-md-10 col-md-offset-1">

            <div class="panel panel-default panel-table">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col col-xs-6">
                            <h3 class="panel-title">${allUsers}</h3>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table class="table table-striped table-bordered table-list">
                        <thead>
                        <tr>
                            <th style="text-align: center"><em class="fa fa-cog"></em></th>
                            <th class="hidden-xs">ID</th>
                            <th>${firstName}</th>
                            <th>${secondName}</th>
                            <th>${phoneNumber}</th>
                            <th>${age}</th>
                            <th>${gender}</th>
                        </tr>
                        </thead>
                        <c:forEach items="${sessionScope.users}" var="user">
                            <tbody>
                            <tr>
                                <td align="center" style="text-align: center">
                                    <form method="post" action="${pageContext.request.contextPath}/bank?command=show_users_credit_cards_command">
                                        <button class="btn btn-default" type="submit"><em class="fa fa-credit-card"></em></button>
                                        <input type="hidden" name="userId" value="${user.id}">
                                    </form>
                                </td>
                                <td class="hidden-xs">${user.id}</td>
                                <td>${user.firstName}</td>
                                <td>${user.secondName}</td>
                                <td>${user.phoneNumber}</td>
                                <td>${user.age}</td>
                                <td>${user.gender}</td>
                            </tr>
                            </tbody>
                        </c:forEach>
                    </table>

                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col col-xs-4">Page 1 of 5
                        </div>
                        <div class="col col-xs-8">
                            <ul class="pagination hidden-xs pull-right">
                                <li><a href="">«</a></li>
                                <li><a href="${pageContext.request.contextPath}/bank?command=show_users_command&page=1">1</a></li>
                                <li><a href="${pageContext.request.contextPath}/bank?command=show_users_command&page=2">2</a></li>
                                <li><a href="${pageContext.request.contextPath}/bank?command=show_users_command&page=3">3</a></li>
                                <li><a href="${pageContext.request.contextPath}/bank?command=show_users_command&page=4">4</a></li>
                                <li><a href="${pageContext.request.contextPath}/bank?command=show_users_command&page=5">5</a></li>
                                <li><a href="#">»</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="common_resource/footer.jsp"/>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</body>
</html>