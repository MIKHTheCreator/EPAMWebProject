<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="logIn" var="logIn"/>
<fmt:message bundle="${loc}" key="enterDataMessage" var="enterData"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
        <title>LogIn Page</title>

        <style>
            <%@include file="/WEB-INF/scss/login.scss"%>
        </style>
    </head>

    <body>
        <jsp:include page="/WEB-INF/jsp/common_resource/header.jsp"/>

        <div class="wrapper">
            <form class="form-signin" action="login.jsp">
              <h2 class="form-signin-heading">${enterData}</h2>
                <label>
                    <input type="text" class="form-control" name="username" placeholder="Username or Email Address" required="" autofocus="" />
                </label>
                <label>
                    <input type="password" class="form-control" name="password" placeholder="Password" required=""/>
                </label>
                <button class="btn btn-lg btn-primary btn-block" type="submit" >${logIn}</button>
            </form>
          </div>
          <div class="footer-dark" style="position: fixed; bottom:0; left:0; width:100%">
            <jsp:include page="common_resource/footer.jsp"/>
        </div>
    </body>
</html>