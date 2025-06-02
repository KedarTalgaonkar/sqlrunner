package com.eeft.ren.sqlrunner.exception;

public class SqlExecutionException extends RuntimeException {

	private static final long serialVersionUID = 4521714444621182018L;

	public SqlExecutionException(String message) {
        super(message);
    }

    public SqlExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
