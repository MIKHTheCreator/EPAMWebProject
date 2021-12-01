<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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

<!DOCTYPE html>
<html>
<head>
    <title>Create Passport Page</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-csss">
</head>

<body>
<%@include file="common_resource/header.jsp"%>
<form class="form-horizontal" action="${pageContext.request.contextPath}/bank?command=save_passport_command" method="post" style="padding-top: 92px; padding-left: 30px">
    <fieldset>
        <div id="legend">
            <legend class="">${fillPassportInfo}</legend>
        </div>
        <div class="control-group">
            <label class="control-label"  for="seriaAndNumber">Seria And Number</label>
            <div class="controls">
                <input type="text" id="seriaAndNumber" name="seriaAndNumber" placeholder="" class="input-xlarge">
                <p class="help-block">${seriaAndNumberMessage}</p>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="personalNumber">Personal Number</label>
            <div class="controls">
                <input type="text" id="personalNumber" name="personalNumber" placeholder="" class="input-xlarge">
                <p class="help-block">${personalNumberMessage}</p>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="expirationDate">Expiration Date</label>
            <div class="controls">
                <input type="date" id="expirationDate" name="expirationDate" placeholder="" class="input-xlarge">
                <p class="help-block">${expirationDate}</p>
            </div>
        </div>

        <div class="control-group">
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