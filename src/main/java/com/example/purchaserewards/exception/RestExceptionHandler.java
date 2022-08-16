package com.example.purchaserewards.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(CustomerNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(CustomerNotFoundException ex) {
        logger.error(ex.getMessage());
        RestApiError restApiError = new RestApiError(BAD_REQUEST);
        restApiError.setMessage(ex.getMessage());
        return buildResponseEntity(restApiError);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAnyException(Exception ex) {
        logger.error(ex.getMessage());
        RestApiError restApiError = new RestApiError(INTERNAL_SERVER_ERROR);
        restApiError.setMessage(ex.getMessage());
        return buildResponseEntity(restApiError);
    }

    private ResponseEntity<Object> buildResponseEntity(RestApiError restApiError) {
        return new ResponseEntity<>(restApiError, restApiError.getStatus());
    }

}
