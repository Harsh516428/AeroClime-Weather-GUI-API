package com.aeroclime.exceptions;

public class DataIngestionException extends Exception {
    public DataIngestionException(String message) {
        super(message);
    }
    public DataIngestionException(String message, Throwable cause) {
        super(message, cause);
    }
}
