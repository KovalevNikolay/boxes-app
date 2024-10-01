package ru.kovalev.boxesapp.exception;

public class FileReadingException extends RuntimeException {
    public FileReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}