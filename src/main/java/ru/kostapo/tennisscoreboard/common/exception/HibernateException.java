package ru.kostapo.tennisscoreboard.common.exception;

public class HibernateException extends RuntimeException {
    public HibernateException(String message) {
        super(message);
    }
}