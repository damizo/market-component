package com.popielarski.market.common.exception;


public class LogicValidationException extends RuntimeException {

    private String message;

    public LogicValidationException(String message) {
        super(message);
        this.message = message;
    }
}
