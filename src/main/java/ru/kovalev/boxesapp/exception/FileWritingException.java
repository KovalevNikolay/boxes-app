package ru.kovalev.boxesapp.exception;

public class FileWritingException extends RuntimeException {
    public FileWritingException(String message, Throwable cause) {
        super(message, cause);
    }
}
