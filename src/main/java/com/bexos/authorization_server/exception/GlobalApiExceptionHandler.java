package com.bexos.authorization_server.exception;

import com.bexos.authorization_server.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalApiExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundExceptionHandler(NotFoundException ex) {
        return ExceptionResponse.builder()
                .message(ex.getMessage())
                .build();
    }
}
