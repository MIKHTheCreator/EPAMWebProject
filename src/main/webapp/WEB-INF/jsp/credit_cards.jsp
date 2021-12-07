<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="addCreditCard" var="addCreditCard"/>
<fmt:message bundle="${loc}" key="ccNumber" var="ccNumber"/>
<fmt:message bundle="${loc}" key="ccExpirationDate" var="ccExpirationDate"/>
<fmt:message bundle="${loc}" key="ccfullName" var="ccfullName"/>
<fmt:message bundle="${loc}" key="ccCurrency" var="ccCurrency"/>
<fmt:message bundle="${loc}" key="ccbalance" var="ccbalance"/>
<fmt:message bundle="${loc}" key="ccIsBlocked" var="ccIsBlocked"/>
<fmt:message bundle="${loc}" key="makePaymentButton" var="makePaymentButton"/>
<fmt:message bundle="${loc}" key="blockUsrButton" var="blockUsrButton"/>
<fmt:message bundle="${loc}" key="unableToPay" var="unableToPay"/>

<!DOCTYPE html>
<html>
<head>
    <title>Credit Cards Page</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <style>
        <%@include file="/WEB-INF/css/credit_card_table.css"%>
    </style>
</head>

<body style="background-color: #FFFFFF">
<%@include file="common_resource/header.jsp"%>
    <div class="container" style="padding-top: 40px; padding-bottom: 40px">
        <div class="row col-md-6 col-md-offset-2 custyle">
            <table class="table table-striped custab">
                <thead>
                <c:choose>
                    <c:when test="${sessionScope.currentUser.role eq Role.USER}">
                        <a href="${pageContext.request.contextPath}/bank?command=show_add_credit_card_page_command" class="btn btn-primary btn-xs pull-right"> ${addCreditCard}</a>
                        <tr>
                            <th>${ccNumber}</th>
                            <th>${ccExpirationDate}</th>
                            <th>${ccfullName}</th>
                            <th>${ccCurrency}</th>
                            <th>${ccbalance}</th>
                            <th>${ccIsBlocked}</th>
                            <th class="text-center">${makePaymentButton}</th>
                        </tr>
                        </thead>
                        <c:forEach items="${sessionScope.creditCards}" var="creditCard">
                            <tr>
                                <td>${creditCard.number}</td>
                                <td>${creditCard.expirationDate}</td>
                                <td>${creditCard.fullName}</td>
                                <td>${creditCard.bankAccount.currency}</td>
                                <td>${creditCard.bankAccount.balance}</td>
                                <td>${creditCard.bankAccount.blocked}</td>
                                <c:choose>
                                    <c:when test="${creditCard.bankAccount.blocked eq false}">
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/bank?command=show_make_payment_page_command" method="post">
                                                <button class="btn btn-info btn-xs" type="submit">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                </button>
                                                <input type="hidden" name="bankAccountId" value="${creditCard.bankAccount.id}">
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${unableToPay}</td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:when test="${sessionScope.currentUser.role eq Role.ADMIN}">
                        <tr>
                            <th>ID</th>
                            <th>${ccNumber}</th>
                            <th>${ccExpirationDate}</th>
                            <th>${ccfullName}</th>
                            <th>${ccCurrency}</th>
                            <th>${ccIsBlocked}</th>
                            <th class="text-center">${blockUsrButton}</th>
                        </tr>
                        </thead>
                        <c:forEach items="${sessionScope.creditCards}" var="creditCard">
                            <tr>
                                <td>${creditCard.id}</td>
                                <td>${creditCard.number}</td>
                                <td>${creditCard.expirationDate}</td>
                                <td>${creditCard.fullName}</td>
                                <td>${creditCard.bankAccount.currency}</td>
                                <td>${creditCard.bankAccount.blocked}</td>
                                <c:choose>
                                    <c:when test="${creditCard.bankAccount.blocked eq false}">
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/bank?command=block_users_bank_account_command" method="post">
                                                <button class="btn btn-info btn-xs" style="background-color: red" type="submit">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                </button>
                                                <input type="hidden" name="bankAccountId" value="${creditCard.bankAccount.id}">
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/bank?command=deblock_users_bank_account_command" method="post">
                                                <button class="btn btn-info btn-xs" style="background-color: #46c1f6" type="submit">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                </button>
                                                <input type="hidden" name="bankAccountId" value="${creditCard.bankAccount.id}">
                                            </form>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </table>
        </div>
    </div>
<jsp:include page="common_resource/footer.jsp"/>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</body>
</html>