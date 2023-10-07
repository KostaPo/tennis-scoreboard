package ru.kostapo.tennisscoreboard.common.exception;

public class BadParameterException extends RuntimeException {
    public BadParameterException(String message) {
        super(message);
    }
}