<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<fmt:message bundle="${loc}" key="fillAccountInfo" var="fillAccountInfo"/>
<fmt:message bundle="${loc}" key="firstNameMessage" var="firstNameMessage"/>
<fmt:message bundle="${loc}" key="secondNameMessage" var="secondNameMessage"/>
<fmt:message bundle="${loc}" key="phoneNumberMessage" var="phoneNumberMessage"/>
<fmt:message bundle="${loc}" key="ageMessage" var="ageMessage"/>
<fmt:message bundle="${loc}" key="submitButton" var="submitButton"/>
<fmt:message bundle="${loc}" key="genderMessage" var="genderMessage"/>
<fmt:message bundle="${loc}" key="gender" var="gender"/>
<fmt:message bundle="${loc}" key="selectGender" var="selectGender"/>
<fmt:message bundle="${loc}" key="male" var="male"/>
<fmt:message bundle="${loc}" key="female" var="female"/>
<fmt:message bundle="${loc}" key="ns" var="ns"/>
<fmt:message bundle="${loc}" key="firstName" var="firstName"/>
<fmt:message bundle="${loc}" key="wrongFirstNameFormat" var="wrongFirstNameFormat"/>
<fmt:message bundle="${loc}" key="secondName" var="scondName"/>
<fmt:message bundle="${loc}" key="wrongSecondNameFormat" var="wrongSecondNameFormat"/>
<fmt:message bundle="${loc}" key="phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${loc}" key="wrongPhoneNumberFormat" var="wrongPhoneNumberFormat"/>
<fmt:message bundle="${loc}" key="age" var="age"/>
<fmt:message bundle="${loc}" key="wrongAgeFormat" var="wrongAgeFormat"/>

<!DOCTYPE html>
<html>
<head>
    <title>Create account Page</title>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-csss">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

</head>

<body style="background: url(https://images.unsplash.com/photo-1611186871348-b1ce696e52c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80) no-repeat center center fixed; background-size: cover">
<%@include file="common_resource/header.jsp"%>
<form class="form-horizontal" action="${pageContext.request.contextPath}/bank?command=edit_user_command" method="post" style="padding-top: 42px; padding-left: 30px">
    <fieldset>
        <div id="legend">
            <legend class="" style="font-weight: bold; font-size: 22px">${editAccountData}</legend>
        </div>
        <div class="control-group">
            <label class="control-label"  for="firstName">${firstName}</label>
            <div class="controls">
                <input type="text" id="firstName" name="firstName" placeholder="" class="input-xlarge" pattern="^[a-zA-Z ,.'-]{0,40}$" title="${wrongFirstNameFormat}" style="height: 30px">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="secondName">${scondName}</label>
            <div class="controls">
                <input type="text" id="secondName" name="secondName" placeholder="" class="input-xlarge" pattern="^[a-zA-Z ,.'-]{0,60}$" title="${wrongSecondNameFormat}" style="height: 30px">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="phoneNumber">${phoneNumber}</label>
            <div class="controls">
                <input type="text" id="phoneNumber" name="phoneNumber" placeholder="" class="input-xlarge" pattern="^375(25|44|29|33)\d{0,7}$" title="${wrongPhoneNumberFormat}" style="height: 30px">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="age">${age}</label>
            <div class="controls">
                <input type="text" id="age" name="age" placeholder="" class="input-xlarge" title="${wrongAgeFormat}" style="height: 30px" required pattern="^\d+$">
            </div>
        </div>

        <div class="control-group">
            <label class="control-label"  for="gender">${gender}</label>
            <div class="controls">
                <select class="span3" name="gender" id="gender">
                    <option value="NS">${selectGender}</option>
                    <option value="MALE">${male}</option>
                    <option value="FEMALE">${female}</option>
                    <option value="NS">${ns}</option>
                </select>
            </div>
        </div>

        <div class="control-group">
            <c:choose>
                <c:when test="${not empty error}">
                    <div class="error box" style="color: red; font-family: Verdana, serif; text-align: left; padding-bottom: 10px; padding-top: 5px; padding-left: 95px">
                            ${error}
                    </div>
                </c:when>
                <c:when test="${not empty message}">
                    <div class="error box" style="color: red; font-family: Verdana, serif; text-align: left; padding-bottom: 10px; padding-top: 5px; padding-left: 95px">
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