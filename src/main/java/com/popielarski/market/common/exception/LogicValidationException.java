package com.popielarski.market.common.exception;


import lombok.Getter;

@Getter
public class LogicValidationException extends RuntimeException {

    private String message;

    public LogicValidationException(String message) {
        super(message);
        this.message = message;
    }
}
