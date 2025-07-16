package com.triviarush.exception;

public class CorrectAnswerNotOptionException extends RuntimeException {
    public CorrectAnswerNotOptionException(final String message) {
        super(message);
    }
}