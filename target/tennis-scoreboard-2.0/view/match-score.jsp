<%@ page import="ru.kostapo.tennisscoreboard.model.score.State" %>
<%@ page import="ru.kostapo.tennisscoreboard.dto.MatchReqDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<style>
    <%@ include file="/view/css/common.css" %>
</style>
<style>
    <%@ include file="/view/css/match-score.css" %>
</style>
<% MatchReqDto match = (MatchReqDto) request.getAttribute("match"); %>
<head>
    <title>TENNIS</title>
</head>
<body>
<div class="main_container">
    <div class="header">
        <h1>МАТЧ # <%= match.getUuid() %>
        </h1>
    </div>
    <div class="tennis-scoreboard">
        <div class="previous-sets">
            <div class="set">
                <div class="field">
                    <div class="player1-set-score"><%= match.getScore().getPlayerSetScore(1, 1) %>
                    </div>
                </div>
                <div class="fields-label">1 сет</div>
                <div class="field">
                    <div class="player2-set-score"><%= match.getScore().getPlayerSetScore(2, 1) %>
                    </div>
                </div>
            </div>
            <div class="set">
                <div class="field">
                    <div class="player1-set-score"><%= match.getScore().getPlayerSetScore(1, 2) %>
                    </div>
                </div>
                <div class="fields-label">2 сет</div>
                <div class="field">
                    <div class="player2-set-score"><%= match.getScore().getPlayerSetScore(2, 2) %>
                    </div>
                </div>
            </div>
            <div class="set">
                <div class="field">
                    <div class="player1-set-score"><%= match.getScore().getPlayerSetScore(1, 3) %>
                    </div>
                </div>
                <div class="fields-label">3 сет</div>
                <div class="field">
                    <div class="player2-set-score"><%= match.getScore().getPlayerSetScore(2, 3) %>
                    </div>
                </div>
            </div>
        </div>
        <div class="players">
            <div class="player1">
                <% if (match.getScore().getState().equals(State.PLAYER_1_WIN)) { %>
                <%= match.getFirstPlayer().getName() + " ✔" %>
                <%} else {%>
                <%= match.getFirstPlayer().getName() %>
                <% } %>
            </div>
            <div class="player2">
                <% if (match.getScore().getState().equals(State.PLAYER_2_WIN)) {%>
                <%= match.getSecondPlayer().getName() + " ✔" %>
                <%} else {%>
                <%= match.getSecondPlayer().getName() %>
                <% } %>
            </div>
        </div>

        <% if (match.getScore().getState().equals(State.GO_ON)) { %>
        <div class="current">
            <div class="current-points">
                <div class="field">
                    <div class="player1-score"><%= match.getScore().getPlayerCurrentGamePoint(1) %>
                    </div>
                </div>
                <div class="fields-label">очки</div>
                <div class="field">
                    <div class="player2-score"><%= match.getScore().getPlayerCurrentGamePoint(2) %>
                    </div>
                </div>
            </div>
        </div>
        <form action="${pageContext.request.contextPath}/match-score?uuid=<%= match.getUuid() %>" method="POST">
            <div class="buttons">
                <button type="submit" onclick="disableButton(this)">
                    <%= match.getFirstPlayer().getName() %>
                    <input type="hidden" name="player" value="2">
                </button>
                <button type="submit" onclick="disableButton(this)">
                    <%= match.getSecondPlayer().getName() %>
                    <input type="hidden" name="player" value="1">
                </button>
            </div>
        </form>
        <script>
            function disableButton(button) {
                button.innerHTML = 'Sending...';
                button.form.submit();
                button.disabled = true;
            }
        </script>
        <% } %>
    </div>
    <% if (!match.getScore().getState().equals(State.GO_ON)) { %>
    <div class="links_container">
        <a href="${pageContext.request.contextPath}/">Возврат на главную</a>
        <% String name; %>
        <% if (match.getScore().getState().equals(State.PLAYER_1_WIN)) { %>
        <% name = match.getFirstPlayer().getName(); %>
        <% } else { %>
        <% name = match.getSecondPlayer().getName(); %>
        <% } %>
        <a href="${pageContext.request.contextPath}/matches?player_name=<%=name%>">Сыгранные матчи</a>
    </div>
    <% } %>
</div>
</body>
</html>