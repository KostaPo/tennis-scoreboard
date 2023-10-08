<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<style>
    <%@ include file="/view/css/common.css" %>
    <%@ include file="/view/css/error.css" %>
</style>
<head>
    <title>TENNIS</title>
</head>
<body>
<div class="main_container">
    <%
        String message = (String) request.getAttribute("exception_message");
    %>
    <div class="header">
        <h1><%= message %>
        </h1>
    </div>
    <div class="links_container">
        <a href="${pageContext.request.contextPath}/">Возврат на главную</a>
    </div>
</div>
</body>
</html>
