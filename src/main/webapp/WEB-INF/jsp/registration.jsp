<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="logHere" var="logHere"/>
<fmt:message bundle="${loc}" key="enterDataMessage" var="enterData"/>
<fmt:message bundle="${loc}" key="provideEmail" var="provideEmail"/>
<fmt:message bundle="${loc}" key="passwordMessage" var="passwordMessage"/>
<fmt:message bundle="${loc}" key="usernameMessage" var="usernameMessage"/>
<fmt:message bundle="${loc}" key="wrongUsernameFormat" var="wrongUsernameFormat"/>
<fmt:message bundle="${loc}" key="wrongEmailFormat" var="wrongEmailFormat"/>
<fmt:message bundle="${loc}" key="wrongPasswordFormat" var="wrongPasswordFormat"/>

<!DOCTYPE html>
<html>
    <head>
        <title>Registration Page</title>
        <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-csss">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    </head>

    <body style="background: url(https://images.unsplash.com/photo-1595126730769-f680ac3f9486?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80) no-repeat center center fixed; background-size: cover">
    <%@include file="common_resource/header.jsp"%>
    <form class="form-horizontal" action="${pageContext.request.contextPath}/bank?command=registration" method="post" style="padding-top: 72px; padding-left: 30px">
        <fieldset style="width:800px">
            <div id="legend">
                <legend class="" style="font-weight:bold; font-size:22px">${registration}</legend>
            </div>
            <div class="control-group">
                <label class="control-label"  for="username">Username</label>
                <div class="controls">
                    <input type="text" id="username" name="username" placeholder="" class="input-xlarge" required pattern="^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$" title="${wrongUsernameFormat}" style="height:30px;">
                    <p class="help-block" style="color: green">${usernameMessage}</p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="email">E-mail</label>
                <div class="controls">
                    <input type="text" id="email" name="email" placeholder="" class="input-xlarge" required pattern="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*)@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])" title="${wrongEmailFormat}" style="height: 30px">
                    <p class="help-block" style="color: green">${provideEmail}</p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="password">Password</label>
                <div class="controls">
                    <input type="password" id="password" name="password" placeholder="" class="input-xlarge" required pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" title="${wrongPasswordFormat}" style="height: 30px">
                    <p class="help-block" style="color: green">${passwordMessage}</p>
                </div>
            </div>

            <div class="control-group">
                <c:choose>
                    <c:when test="${not empty error}">
                        <div class="error box" style="color: red; font-family: Verdana, serif; text-align: left; padding-top: 5px; padding-bottom: 10px; padding-left: 95px">
                                ${error}
                        </div>
                    </c:when>
                    <c:when test="${not empty message}">
                        <div class="error box" style="color: red; font-family: Verdana, serif; text-align: left; padding-top: 5px; padding-bottom: 10px; padding-left: 95px">
                                ${message}
                        </div>
                    </c:when>
                </c:choose>
                <div class="controls">
                    <button class="btn btn-success">${registration}</button>
                </div>
            </div>
        </fieldset>
    </form>
    <jsp:include page="common_resource/footer.jsp"/>
    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    </body>
</html>