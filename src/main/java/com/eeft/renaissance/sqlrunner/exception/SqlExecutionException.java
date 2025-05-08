package com.eeft.renaissance.sqlrunner.exception;

public class SqlExecutionException extends RuntimeException {

    public SqlExecutionException(String message) {
        super(message);
    }

    public SqlExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
