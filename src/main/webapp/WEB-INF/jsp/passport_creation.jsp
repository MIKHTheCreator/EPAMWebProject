<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="fillPassportInfo" var="fillPassportInfo"/>
<fmt:message bundle="${loc}" key="seriaAndNumberMessage" var="seriaAndNumberMessage"/>
<fmt:message bundle="${loc}" key="personalNumberMessage" var="personalNumberMessage"/>
<fmt:message bundle="${loc}" key="expirationDate" var="expirationDate"/>
<fmt:message bundle="${loc}" key="submitButton" var="submitButton"/>
<fmt:message bundle="${loc}" key="wrongPassportSeriaAndNumber" var="wrongPassportSeriaAndNumber"/>
<fmt:message bundle="${loc}" key="wrongPassportNumber" var="wrongPassportNumber"/>
<fmt:message bundle="${loc}" key="seriaAndNumber" var="seriaAndNumber"/>
<fmt:message bundle="${loc}" key="personalNumber" var="personalNumber"/>
<fmt:message bundle="${loc}" key="expirationDateHeader" var="expirationDateHeader"/>

<!DOCTYPE html>
<html>
<head>
    <title>Create Passport Page</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-csss">
</head>

<body style="background: url(https://images.unsplash.com/photo-1614599467445-afb0d1a01fc3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80) no-repeat center center fixed; background-size: cover">
<%@include file="common_resource/header.jsp"%>
<form class="form-horizontal" action="${pageContext.request.contextPath}/bank?command=save_passport_command" method="post" style="padding-top: 92px; padding-left: 30px">
    <fieldset style="width: 800px">
        <div id="legend">
            <legend class="" style="font-size: 22px; font-weight: bold">${fillPassportInfo}</legend>
        </div>
        <div class="control-group">
            <label class="control-label"  for="seriaAndNumber">${seriaAndNumber}</label>
            <div class="controls">
                <input type="text" id="seriaAndNumber" name="seriaAndNumber" placeholder="" class="input-xlarge" required pattern="^([A-Z]{2})(\d{7})$" title="${wrongPassportSeriaAndNumber}" style="height: 30px">
                <p class="help-block" style="color: green">${seriaAndNumberMessage}</p>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="personalNumber">${personalNumber}</label>
            <div class="controls">
                <input type="text" id="personalNumber" name="personalNumber" placeholder="" class="input-xlarge" required pattern="^[3-6]{1}(0[1-9]|[12]\d|3[01])(0[1-9]|1[1-9])\d\d[A-Z]{1}(\d{3})(PP|PB)([0-9])$" title="${wrongPassportNumber}" style="height: 30px">
                <p class="help-block" style="color: green">${personalNumberMessage}</p>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="expirationDate">${expirationDateHeader}</label>
            <div class="controls">
                <input type="date" id="expirationDate" name="expirationDate" placeholder="" class="input-xlarge" required style="height: 30px;">
                <p class="help-block" style="color: green">${expirationDate}</p>
            </div>
        </div>

        <div class="control-group">
            <c:choose>
                <c:when test="${not empty error}">
                    <div class="error box" style="color: red; font-family: Verdana, serif; text-align: left; padding-top: 10px; padding-bottom: 10px; padding-left: 95px">
                            ${error}
                    </div>
                </c:when>
                <c:when test="${not empty message}">
                    <div class="error box" style="color: red; font-family: Verdana, serif; text-align: left; padding-top: 10px; padding-bottom: 10px; padding-left: 95px">
                            ${message}
                    </div>
                </c:when>
            </c:choose>
            <div class="controls">
                <button class="btn btn-success">${submitButton}</button>
            </div>
        </div>
    </fieldset>
</form>
<jsp:include page="common_resource/footer.jsp"/>
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</body>
</html>