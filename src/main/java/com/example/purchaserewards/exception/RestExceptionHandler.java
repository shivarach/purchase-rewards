package com.example.purchaserewards.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(CustomerNotFoundException ex) {
        log.error(ex.getMessage());
        RestApiError restApiError = new RestApiError(BAD_REQUEST);
        restApiError.setMessage(ex.getMessage());
        return buildResponseEntity(restApiError);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAnyException(Exception ex) {
        log.error(ex.getMessage());
        RestApiError restApiError = new RestApiError(INTERNAL_SERVER_ERROR);
        restApiError.setMessage(ex.getMessage());
        return buildResponseEntity(restApiError);
    }

    private ResponseEntity<Object> buildResponseEntity(RestApiError restApiError) {
        return new ResponseEntity<>(restApiError, restApiError.getStatus());
    }

}
