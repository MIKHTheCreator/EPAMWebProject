<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="paymentDetails" var="paymentDetails"/>
<fmt:message bundle="${loc}" key="sum" var="sum"/>
<fmt:message bundle="${loc}" key="paymentOrganization" var="paymentOrganization"/>
<fmt:message bundle="${loc}" key="paymentGoal" var="paymentGoal"/>
<fmt:message bundle="${loc}" key="pay" var="pay"/>

<!DOCTYPE html>
<html>
<head>
    <title>Payment Page</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://use.fontawesome.com/releases/v5.7.2/css/all.css" rel="stylesheet">

    <style>
        <%@include file="/WEB-INF/css/payment.css"%>
    </style>
</head>

<body>
<%@include file="common_resource/header.jsp"%>
<form action="${pageContext.request.contextPath}/bank?command=save_payment_command">
    <div class="container p-0">
        <div class="card px-4">
            <p class="h8 py-3">${paymentDetails}</p>
            <div class="row gx-3">
                <div class="col-12">
                    <div class="d-flex flex-column">
                        <p class="text mb-1">${sum}</p>
                        <input class="form-control mb-3" type="text" placeholder="sum" value="150.0">
                    </div>
                </div>
                <div class="col-12">
                    <div class="d-flex flex-column">
                        <p class="text mb-1">${paymentOrganization}</p>
                        <input class="form-control mb-3" type="text" placeholder="organization" value="Belinvest Bank">
                    </div>
                </div>
                <div class="col-12">
                    <div class="d-flex flex-column">
                        <p class="text mb-1">${paymentGoal}</p>
                        <input class="form-control mb-3" type="text" placeholder="goal" value="Charity">
                    </div>
                </div>

                <div class="col-12">
                    <div class="btn btn-primary mb-3">
                        <span class="ps-3">${pay}</span>
                        <span class="fas fa-arrow-right"></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<jsp:include page="common_resource/footer.jsp"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</body>
</html>