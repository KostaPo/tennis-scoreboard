package ru.kostapo.tennisscoreboard.controller;

import ru.kostapo.tennisscoreboard.dto.PageReqDto;
import ru.kostapo.tennisscoreboard.dto.PageResDto;
import ru.kostapo.tennisscoreboard.model.Match;
import ru.kostapo.tennisscoreboard.dao.MatchDAO;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MatchesController", value = "/matches")
public class MatchesController extends HttpServlet {

    private MatchDAO matchDAO;

    @Override
    public void init(ServletConfig config) {
        matchDAO = (MatchDAO) config.getServletContext().getAttribute("matchDAO");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            PageReqDto pageRequest = new PageReqDto(
                    request.getParameter("page"),
                    request.getParameter("player_name"));
            PageResDto<Match> responsePage = matchDAO.getPage(pageRequest);
            request.setAttribute("responsePage", responsePage);
            request.getRequestDispatcher("view/matches.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("exception_message", ex.getMessage());
            request.getRequestDispatcher("view/error.jsp").forward(request, response);
        }
    }
}
