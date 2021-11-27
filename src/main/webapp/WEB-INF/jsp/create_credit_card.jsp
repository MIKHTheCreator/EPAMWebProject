<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="ctg" uri="customtag" %>

<fmt:setLocale value="${not empty sessionScope.language ? sessionScope.language : 'en'}"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="createCreditCard" var="createCreditCard"/>
<fmt:message bundle="${loc}" key="ccfullName" var="fullName"/>
<fmt:message bundle="${loc}" key="ccNumber" var="creditCardNumber"/>
<fmt:message bundle="${loc}" key="expirationDate" var="expirationDate"/>
<fmt:message bundle="${loc}" key="cvv" var="cvv"/>
<fmt:message bundle="${loc}" key="ccCurrency" var="currency"/>
<fmt:message bundle="${loc}" key="createButton" var="createButton"/>

<!DOCTYPE html>
<html>
<head>
    <title>Create Credit Card Page</title>
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-css">
</head>

<body>
<%@include file="common_resource/header.jsp"%>
<div class="container">
    <div class="row-fluid">
        <form class="form-horizontal" action="${pageContext.request.contextPath}/bank?command=save_credit_card_command" method="post">
            <fieldset>
                <div id="legend">
                    <legend class="">${createCreditCard}</legend>
                </div>

                <div class="control-group">
                    <label class="control-label"  for="fullName">${fullName}</label>
                    <div class="controls">
                        <input type="text" id="fullName" name="fullName" placeholder="" class="input-xlarge">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="creditCardNumber">${creditCardNumber}</label>
                    <div class="controls">
                        <input type="text" id="creditCardNumber" name="creditCardNumber" placeholder="" class="input-xlarge">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label">${expirationDate}</label>
                    <div class="controls">
                        <select class="span3" name="expirationMonth" id="expirationMonth">
                            <option></option>
                            <option value="01">Jan (01)</option>
                            <option value="02">Feb (02)</option>
                            <option value="03">Mar (03)</option>
                            <option value="04">Apr (04)</option>
                            <option value="05">May (05)</option>
                            <option value="06">June (06)</option>
                            <option value="07">July (07)</option>
                            <option value="08">Aug (08)</option>
                            <option value="09">Sep (09)</option>
                            <option value="10">Oct (10)</option>1
                            <option value="11">Nov (11)</option>
                            <option value="12">Dec (12)</option>
                        </select>
                        <select class="span2" name="expirationYear" id="expirationYear">
                            <option value="13">2021</option>
                            <option value="14">2022</option>
                            <option value="15">2023</option>
                            <option value="16">2024</option>
                            <option value="17">2025</option>
                            <option value="18">2026</option>
                            <option value="19">2027</option>
                            <option value="20">2028</option>
                            <option value="21">2029</option>
                            <option value="22">2030</option>
                            <option value="23">2031</option>
                        </select>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label"  for="cvv">${cvv}</label>
                    <div class="controls">
                        <input type="password" id="cvv" name="cvv" placeholder="" class="span2">
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label"  for="currency">${currency}</label>
                    <div class="controls">
                        <input type="text" id="currency" name="currency" placeholder="" class="span2">
                    </div>
                </div>


                <div class="control-group">
                    <div class="controls">
                        <button class="btn btn-success">${createButton}</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>
<jsp:include page="common_resource/footer.jsp"/>
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</body>
</html>