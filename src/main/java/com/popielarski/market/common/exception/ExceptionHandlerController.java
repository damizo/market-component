package com.popielarski.market.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
class ExceptionHandlerController {


    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(LogicValidationException.class)
    public ErrorDTO handle(LogicValidationException ex) {
        log.error("LogicValidationException occurred: {}", ex.getMessage());
        return new ErrorDTO(ex.getMessage(), ex.getStackTrace());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDTO handle(IllegalArgumentException ex) {
        log.error("IllegalArgumentException occurred: {}", ex.getMessage());
        return new ErrorDTO(ex.getMessage(), ex.getStackTrace());
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnsupportedOperationException.class)
    public ErrorDTO handle(UnsupportedOperationException ex) {
        log.error("UnsupportedOperationException occurred: {}", ex.getMessage());
        return new ErrorDTO(ex.getMessage(), ex.getStackTrace());
    }

    @Getter
    @AllArgsConstructor
    private class ErrorDTO {

        private String message;
        private StackTraceElement[] stackTrace;

    }
}
