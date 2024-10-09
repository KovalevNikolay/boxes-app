package ru.kovalev.boxesapp.exception;

public class SendMessageException extends RuntimeException {

    public SendMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
