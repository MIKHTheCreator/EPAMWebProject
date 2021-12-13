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
<fmt:message bundle="${loc}" key="alreadyBlocked" var="alreadyBlocked"/>
<fmt:message bundle="${loc}" key="blockButton" var="blockButton"/>
<fmt:message bundle="${loc}" key="deleteCreditCard" var="deleteCreditCard"/>
<fmt:message bundle="${loc}" key="language" var="language"/>
<fmt:message bundle="${loc}" key="social" var="social"/>
<fmt:message bundle="${loc}" key="adress" var="adress"/>

<!DOCTYPE html>
<html>
<head>
    <title>Credit Cards Page</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
    <style>
        <%@include file="/WEB-INF/css/common_footer.css"%>
    </style>
    <style>
        <%@include file="/WEB-INF/css/credit_card_table.css"%>
    </style>
</head>

<body style="background-color: #FFFFFF">
<%@include file="common_resource/header.jsp"%>
    <div class="container" style="padding-top: 120px; padding-bottom: 90px">
        <div class="row col-md-6 col-md-offset-2 custyle">
            <table class="table table-striped custab">
                <thead>
                <c:choose>
                    <c:when test="${sessionScope.currentUser.role eq Role.USER}">
                        <a href="${pageContext.request.contextPath}/bank?command=show_add_credit_card_page_command" class="btn btn-primary btn-xs pull-right" style="height:30px; text-align:center; font-size:14px; font-weight:bold; padding-bottom:5px"> ${addCreditCard}</a>
                        <tr>
                            <th>${ccNumber}</th>
                            <th>${ccExpirationDate}</th>
                            <th>${ccfullName}</th>
                            <th>${ccCurrency}</th>
                            <th>${ccbalance}</th>
                            <th>${ccIsBlocked}</th>
                            <th class="text-center">${makePaymentButton}</th>
                            <th class="text-center">${blockButton}</th>
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
                                <c:choose>
                                    <c:when test="${creditCard.bankAccount.blocked eq false}">
                                        <td class="text-center">
                                            <form action="${pageContext.request.contextPath}/bank?command=block_users_bank_account_command" method="post">
                                                <button class="btn btn-info btn-xs" style="background-color: red" type="submit">
                                                    <span class="glyphicon glyphicon-edit"></span>
                                                </button>
                                                <input type="hidden" name="bankAccountId" value="${creditCard.bankAccount.id}">
                                                <input type="hidden" name="userId" value="${sessionScope.currentUser.id}">
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>${alreadyBlocked}</td>
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
                            <th class="text-center">${deleteCreditCard}</th>
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
                                                <input type="hidden" name="userId" value="${requestScope.userId}">
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
                                                <input type="hidden" name="userId" value="${requestScope.userId}">
                                            </form>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/bank?command=delete_credit_card_command" method="post">
                                        <button class="btn btn-info btn-xs" style="background-color: red" type="submit">
                                            <span class="glyphicon glyphicon-floppy-remove"></span>
                                        </button>
                                        <input type="hidden" name="bankAccountId" value="${creditCard.bankAccount.id}">
                                        <input type="hidden" name="userId" value="${requestScope.userId}">
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </table>
        </div>
    </div>
<div class="page">
    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="col-lg-5 col-md-5 col-sm-4 col-xs-12">
                    <ul class="adress">
                        <span>${adress}</span>
                        <li>
                            <p>Minsk, Republic of Belarus</p>
                        </li>

                        <li>
                            <p>+375 25 733 67 13</p>
                        </li>

                        <li>
                            <p>kharevich.ifconfig@gmail.com</p>
                        </li>
                        <li>
                            <p>MIP © 2021</p>
                        </li>
                    </ul>
                </div>


                <div class="col-lg-3 col-md-3 col-sm-4 col-xs-12">
                    <ul class="social">
                        <span>${social}</span>
                        <li>
                            <a href="https://facebook.com"><i class="fa fa-facebook fa-2x" style="font-size: 24px"></i></a>
                        </li>

                        <li>
                            <a href="https://github.com/MIKHTheCreator"><i class="fa fa-github fa-2x" style="font-size: 24px"></i></a>
                        </li>
                        <li>
                            <a href="https://instagram.com"><i class="fa fa-instagram fa-2x" style="font-size: 24px"></i></a>
                        </li>
                        <li>
                            <a href="https://twitter.com"><i class="fa fa-twitter fa-2x" style="font-size: 24px"></i></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </footer>
</div>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</body>
</html>