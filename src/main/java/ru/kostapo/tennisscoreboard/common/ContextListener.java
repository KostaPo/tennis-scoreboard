package ru.kostapo.tennisscoreboard.common;

import org.h2.tools.Server;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.kostapo.tennisscoreboard.dao.MatchDAO;
import ru.kostapo.tennisscoreboard.common.exception.HibernateException;
import ru.kostapo.tennisscoreboard.service.FinishedMatchesService;
import ru.kostapo.tennisscoreboard.service.MatchScoreService;
import ru.kostapo.tennisscoreboard.service.OngoingMatchesService;
import ru.kostapo.tennisscoreboard.utils.HibernateUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.stream.Collectors;

@WebListener
public class ContextListener implements ServletContextListener {

    private Server tcpServer;
    private SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            tcpServer = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
            tcpServer.start();
        } catch (SQLException ex) {
            throw new HibernateException("can't create tcp server");
        }

        ServletContext context = servletContextEvent.getServletContext();

        sessionFactory = HibernateUtil.getSessionFactory();
        executeInitSqlScript(sessionFactory);
        context.setAttribute("SessionFactory", sessionFactory);

        MatchDAO matchDAO = new MatchDAO();
        context.setAttribute("matchDAO", matchDAO);

        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        context.setAttribute("ongoingMatchesService", ongoingMatchesService);

        MatchScoreService matchScoreService = new MatchScoreService(ongoingMatchesService);
        context.setAttribute("matchScoreCalculationService", matchScoreService);

        FinishedMatchesService finishedMatchesService = new FinishedMatchesService(ongoingMatchesService);
        context.setAttribute("finishedMatchesPersistenceService", finishedMatchesService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (tcpServer != null)
            tcpServer.stop();
        if (sessionFactory != null)
            sessionFactory.close();
    }

    private void executeInitSqlScript(SessionFactory sessionFactory) {
        try (InputStream inputStream = HibernateUtil.class.getClassLoader().getResourceAsStream("init.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String sqlScript = reader.lines().collect(Collectors.joining("\n"));
            try (Session session = sessionFactory.openSession()) {
                session.beginTransaction();
                session.createNativeQuery(sqlScript).executeUpdate();
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            throw new HibernateException("can't execute SQL script" + e);
        }
    }
}
