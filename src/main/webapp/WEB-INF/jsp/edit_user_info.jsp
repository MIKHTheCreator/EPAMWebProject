<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="editAccountData" var="editAccountData"/>
<fmt:message bundle="${loc}" key="firstNameMessage" var="firstNameMessage"/>
<fmt:message bundle="${loc}" key="secondNameMessage" var="secondNameMessage"/>
<fmt:message bundle="${loc}" key="phoneNumberMessage" var="phoneNumberMessage"/>
<fmt:message bundle="${loc}" key="ageMessage" var="ageMessage"/>
<fmt:message bundle="${loc}" key="submitButton" var="submitButton"/>
<fmt:message bundle="${loc}" key="genderMessage" var="genderMessage"/>

<!DOCTYPE html>
<html>
<head>
    <title>Create account Page</title>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-csss">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

</head>

<body>
<%@include file="common_resource/header.jsp"%>
<form class="form-horizontal" action="${pageContext.request.contextPath}/bank?command=edit_user_command" method="post" style="padding-top: 42px; padding-left: 30px">
    <fieldset>
        <div id="legend">
            <legend class="">${editAccountData}</legend>
        </div>
        <div class="control-group">
            <label class="control-label"  for="firstName">First Name</label>
            <div class="controls">
                <input type="text" id="firstName" name="firstName" placeholder="" class="input-xlarge">
                <p class="help-block">${firstNameMessage}</p>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="secondName">Second Name</label>
            <div class="controls">
                <input type="text" id="secondName" name="secondName" placeholder="" class="input-xlarge">
                <p class="help-block">${secondNameMessage}</p>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="phoneNumber">Phone Number</label>
            <div class="controls">
                <input type="text" id="phoneNumber" name="phoneNumber" placeholder="" class="input-xlarge">
                <p class="help-block">${phoneNumberMessage}</p>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="age">Age</label>
            <div class="controls">
                <input type="text" id="age" name="age" placeholder="" class="input-xlarge">
                <p class="help-block">${ageMessage}</p>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="gender">Gender</label>
            <div class="controls">
                <input type="text" id="gender" name="gender" placeholder="" class="input-xlarge">
                <p class="help-block">${genderMessage}</p>
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