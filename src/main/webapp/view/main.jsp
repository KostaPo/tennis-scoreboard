<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<style>
    <%@ include file="/view/css/common.css" %>
</style>
<style>
    <%@ include file="/view/css/main.css" %>
</style>
<head>
    <title>TENNIS</title>
</head>
<body>
<div class="main_container">
    <div class="header">
        <h1>БОЛЬШОЙ ТЕННИС</h1>
    </div>
    <div class="links_container">
        <a href="${pageContext.request.contextPath}/new-match">Новый матч</a>
        <a href="${pageContext.request.contextPath}/matches">Сыгранные матчи</a>
    </div>
</div>
</body>
</html>