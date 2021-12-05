<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="logHere" var="logHere"/>
<fmt:message bundle="${loc}" key="enterDataMessage" var="enterData"/>
<fmt:message bundle="${loc}" key="or" var="orr"/>

<!DOCTYPE html>
<html>
    <head>
        <title>LogIn Page</title>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <style>
            <%@include file="/WEB-INF/css/login.css"%>
        </style>
    </head>

    <body style="background: url(https://images.unsplash.com/photo-1634824116737-8bef78f09222?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1133&q=80) no-repeat center center fixed; background-size: cover">
    <%@include file="common_resource/header.jsp"%>
    <div class="center">
        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">${enterData}</h3>
                        </div>
                        <div class="panel-body">
                            <c:choose>
                                <c:when test="${not empty error}">
                                    <div class="error box" style="color: red; font-family: Verdana, serif; text-align: center; padding: 5px">
                                        ${error}
                                    </div>
                                </c:when>
                                <c:when test="${not empty message}">
                                    <div class="error box" style="color: red; font-family: Verdana, serif; text-align: center; padding: 5px">
                                            ${message}
                                    </div>
                                </c:when>
                            </c:choose>
                            <form accept-charset="UTF-8" role="form" action="${pageContext.request.contextPath}/bank?command=login" method="post">
                                <fieldset>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="username" name="username" type="text" required>
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" placeholder="password" name="password" type="password" required>
                                    </div>
                                    <input class="btn btn-lg btn-success btn-block" type="submit" value="Login">
                                </fieldset>
                            </form>
                            <hr/>
                            <center><h4>${orr}</h4></center>
                            <a class="btn btn-lg btn-success btn-block" href="${pageContext.request.contextPath}/bank?command=show_registration_page_command" style="background-color: #3b5999"> Create Account</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="common_resource/footer.jsp"/>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

    </body>
</html>