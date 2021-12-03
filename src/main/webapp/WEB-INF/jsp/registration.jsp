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

<!DOCTYPE html>
<html>
    <head>
        <title>Registration Page</title>
        <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-csss">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    </head>

    <body style="background: -webkit-linear-gradient(left, #3931af, #00c6ff);">
    <%@include file="common_resource/header.jsp"%>
    <form class="form-horizontal" action="${pageContext.request.contextPath}/bank?command=registration" method="post" style="padding-top: 72px; padding-left: 30px">
        <fieldset>
            <div id="legend">
                <legend class="">${registration}</legend>
            </div>
            <div class="control-group">
                <label class="control-label"  for="username">Username</label>
                <div class="controls">
                    <input type="text" id="username" name="username" placeholder="" class="input-xlarge">
                    <p class="help-block">${usernameMessage}</p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="email">E-mail</label>
                <div class="controls">
                    <input type="text" id="email" name="email" placeholder="" class="input-xlarge">
                    <p class="help-block">${provideEmail}</p>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="password">Password</label>
                <div class="controls">
                    <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
                    <p class="help-block">${passwordMessage}</p>
                </div>
            </div>

            <div class="control-group">
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