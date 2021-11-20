<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
    <head>
        <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@600;900&display=swap" rel="stylesheet">
        <style>
            <%@include file="/WEB-INF/css/error.css"%>
        </style>
        <title>Error Page</title>
    </head>
    <body>
        <div class="mainbox">
        <div class="err">4</div>
        <div class="err2">0</div>
        <div class="err3">4</div>
        <div class="msg">Maybe this page moved? Got deleted? Is hiding out in quarantine? Never existed in the first place? The URL is incorrect?
            <p>Let's go to the 
            <a href="${pageContext.request.contextPath}/bank?command=show_main_page_command">main page</a>
             and try again.</p>
            </div>
        </div>
    </body>

</html>