<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<style>
    <%@ include file="/view/css/common.css" %>
    <%@ include file="/view/css/new-match.css" %>
</style>
<head>
    <title>TENNIS</title>
</head>
<body>

<div class="main_container">
    <div class="header">
        <h1>НОВЫЙ МАТЧ</h1>
    </div>
    <div class="new_match_form">
        <form action="${pageContext.request.contextPath}/new-match" method="post" onsubmit="return validateForm()" accept-charset="UTF-8">
            <label for="player1">Игрок 1:</label>
            <input type="text" name="player1" id="player1"><br>
            <label for="player2">Игрок 2:</label>
            <input type="text" name="player2" id="player2"><br>
            <input type="submit" value="Начать">
        </form>
        <script>
            function validateForm() {
                const input1 = document.getElementById("player1").value;
                if (input1 == "") {
                    alert("Введите имя игрока 1");
                    return false;
                }
                const input2 = document.getElementById("player2").value;
                if (input2 == "") {
                    alert("Введите имя игрока 2");
                    return false;
                }
            }
        </script>
    </div>
    <div class="main_menu_link">
        <a href="${pageContext.request.contextPath}/">Возврат на главную</a>
    </div>
</div>
</body>
</html>