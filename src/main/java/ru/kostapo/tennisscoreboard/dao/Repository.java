package ru.kostapo.tennisscoreboard.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.kostapo.tennisscoreboard.utils.HibernateUtil;

public interface Repository<T> {

    default T save(final T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T match = (T) session.merge(entity);
            transaction.commit();
            return match;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new HibernateException("can't save or update");
        }
    }
}
