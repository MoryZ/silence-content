package com.old.silence.code.generator.exception;

/**
 * @author moryzang
 */
public class RuleExecutionException extends RuntimeException {
    public RuleExecutionException(String message) {
        super(message);
    }

    public RuleExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}