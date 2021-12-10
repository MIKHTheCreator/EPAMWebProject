<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="payments" var="payments"/>
<fmt:message bundle="${loc}" key="sum" var="paymentSum"/>
<fmt:message bundle="${loc}" key="paymentDate" var="paymentDate"/>
<fmt:message bundle="${loc}" key="paymentOrganization" var="paymentOrganization"/>
<fmt:message bundle="${loc}" key="paymentGoal" var="paymentGoal"/>

<!DOCTYPE html>
<html>
<head>
    <title>Credit Cards Page</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-csss">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.4.0/css/font-awesome.min.css" rel='stylesheet' type='text/css'>
    <style>
        <%@include file="/WEB-INF/css/payments.css"%>
    </style>
</head>

<body style="background: url(https://images.unsplash.com/flagged/photo-1554386690-8627e1041100?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1233&q=80) no-repeat center center fixed; background-size: cover">
<%@include file="common_resource/header.jsp"%>
<div class="container" style="padding-top: 40px; padding-bottom: 40px">
    <div class="row">
        <p></p>

        <div class="col-md-10 col-md-offset-1">

            <div class="panel panel-default panel-table">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col col-xs-6">
                            <h3 class="panel-title">${payments}</h3>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <table class="table table-striped table-bordered table-list">
                        <thead>
                        <tr>
                            <th>${paymentSum}</th>
                            <th>${paymentDate}</th>
                            <th>${paymentOrganization}</th>
                            <th>${paymentGoal}</th>
                        </tr>
                        </thead>
                        <c:forEach items="${sessionScope.payments}" var="payment">
                            <tbody>
                            <tr>
                                <td>${payment.sumOfPayment}</td>
                                <td>${payment.dateOfPayment}</td>
                                <td>${payment.paymentOrganization}</td>
                                <td>${payment.paymentGoal}</td>
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
                                <li><a href="#">«</a></li>
                                <li><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">5</a></li>
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