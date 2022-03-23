package com.medical.medonline.middleware;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.medical.medonline.dto.response.ErrorResponse;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> resourceValidationException(ValidationException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceValidationException(EntityNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), 4040), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ErrorResponse> resourceValidationException(JsonMappingException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), 4041), HttpStatus.BAD_REQUEST);
    }
}