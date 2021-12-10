<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="paymentDetails" var="paymentDetails"/>
<fmt:message bundle="${loc}" key="sum" var="sum"/>
<fmt:message bundle="${loc}" key="paymentOrganization" var="paymentOrganization"/>
<fmt:message bundle="${loc}" key="paymentGoal" var="paymentGoal"/>
<fmt:message bundle="${loc}" key="pay" var="pay"/>
<fmt:message bundle="${loc}" key="wrongSumFormat" var="wrongSumFormat"/>
<fmt:message bundle="${loc}" key="wrongOrganisationFormat" var="wrongOrganisationFormat"/>
<fmt:message bundle="${loc}" key="wrongGoalFormat" var="wrongGoalFormat"/>
<fmt:message bundle="${loc}" key="ccCurrency" var="currency"/>
<fmt:message bundle="${loc}" key="selectCurrency" var="selectCurrency"/>
<fmt:message bundle="${loc}" key="selectGoal" var="selectGoal"/>
<fmt:message bundle="${loc}" key="bankReplenishment" var="bankReplenishment"/>
<fmt:message bundle="${loc}" key="charity" var="charity"/>
<fmt:message bundle="${loc}" key="taxpayment" var="taxpayment"/>
<fmt:message bundle="${loc}" key="bankDeposit" var="bankDeposit"/>

<%@ page import="java.time.LocalDate" %>

<!DOCTYPE html>
<html>
<head>
    <title>Payment Page</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-csss">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
</head>

<body style="background: url(https://images.unsplash.com/photo-1592495981488-073153776d9a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1212&q=80) no-repeat center center fixed; background-size: cover">
<%@include file="common_resource/header.jsp"%>
<div class="container" style="padding-top: 90px">
    <h2 class="text-center" style="color: #46c1f6; font-size: 36px;">Globo Bank System</h2>
    <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-6 pb-5">
            <form action="${pageContext.request.contextPath}/bank?command=save_payment_command&date=${LocalDate.now()}&bankAccountId=${pageContext.request.getParameter("bankAccountId")}" method="post">
                <div class="card border-primary rounded-0">
                    <div class="card-header p-0">
                        <div class="bg-info text-white text-center py-2">
                            <h3><i class="fa fa-envelope"></i> ${paymentDetails}</h3>
                        </div>
                    </div>
                    <div class="card-body p-3">

                        <div class="form-group">
                            <div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text"><i class="fa fa-user text-info"></i></div>
                                </div>
                                <input type="text" class="form-control" id="sum" name="sum" placeholder="${sum}" required pattern="^[\d\\.\\,]+$" title="${wrongSumFormat}">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group mb-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text"><i class="fa fa-envelope text-info"></i></div>
                                </div>
                                <input type="text" class="form-control" id="organization" name="organization" placeholder="${paymentOrganization}" required pattern="^[\w\d\-\.\:\(\)\;\s]{1,45}$" title="${wrongOrganisationFormat}">
                            </div>
                        </div>
                        <div class="control-group" style="padding-bottom: 10px">
                            <label class="control-label"  for="goal">${paymentGoal}</label>
                            <div class="controls">
                                <select class="span3" name="goal" id="goal" required>
                                    <option value="Bank Account Replenishment">${selectGoal}</option>
                                    <option value="Bank Account Replenishment">${bankReplenishment}</option>
                                    <option value="Charity">${charity}</option>
                                    <option value="Tax payment">${taxpayment}</option>
                                    <option value="Bank Deposit">${bankDeposit}</option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group" style="padding-bottom: 10px">
                            <label class="control-label"  for="currency">${currency}</label>
                            <div class="controls">
                                <select class="span3" name="currency" id="currency" required>
                                    <option value="USD">${selectCurrency}</option>
                                    <option value="EUR">EUR</option>
                                    <option value="BYN">BYN</option>
                                    <option value="USD">USD</option>
                                </select>
                            </div>
                        </div>
                        <div class="text-center">
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
                            <input type="submit" value="${pay}" class="btn btn-info btn-block rounded-0 py-2">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="common_resource/footer.jsp"/>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</body>
</html>