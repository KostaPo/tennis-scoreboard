<%@ page import="ru.kostapo.tennisscoreboard.model.Match" %>
<%@ page import="ru.kostapo.tennisscoreboard.dto.PageResDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<style>
    <%@ include file="/view/css/common.css" %>
    <%@ include file="/view/css/matches.css" %>
</style>
<head>
    <title>TENNIS</title>
</head>
<body>
<div class="main_container">
    <div class="header">
        <h1>СЫГРАННЫЕ МАТЧИ</h1>
    </div>
    <% PageResDto<Match> responsePage = (PageResDto<Match>) request.getAttribute("responsePage"); %>

    <div class="matches_table">
        <form name="filter" method="GET" action="/matches" onsubmit="return validateForm()">
            <input type="text" name="player_name" placeholder="Имя игрока" />
            <button type="submit">Найти</button>
        </form>
        <script>
            function validateForm() {
                const playerName = document.forms["filter"]["player_name"].value;
                if (playerName == "") {
                    alert("Введите имя игрока");
                    return false;
                }
            }
        </script>
        <a href="/matches">
            <button>Сбросить</button>
        </a>

        <table>
            <tr>
                <th>Игрок #1</th>
                <th>Игрок #2</th>
            </tr>
            <% for (Match match : responsePage.getData()) { %>
            <tr>
                <td><%= match.getPlayer1().getName() %>
                    <%= match.getWinner().getName().equals(match.getPlayer1().getName()) ? "✔" : "" %></td>
                <td><%= match.getPlayer2().getName() %>
                    <%= match.getWinner().getName().equals(match.getPlayer2().getName()) ? "✔" : "" %></td>
            </tr>
            <% } %>
        </table>
        <div class="pages_container">
            <% for (int pageNumber = 1; pageNumber <= responsePage.getTotalPages(); pageNumber++) { %>
            <% String pageUrl = "/matches?page=" + pageNumber; %>
            <% if (responsePage.isWithFilter()) { %>
            <% pageUrl += "&player_name=" + responsePage.getFilterKey(); %>
            <% } %>
            <a href="<%= pageUrl %>"><%= pageNumber %></a>
            <% } %>
        </div>
    </div>
    <div class="main_menu_link">
        <a href="/">Возврат на главную</a>
    </div>

</div>
</body>
</html>