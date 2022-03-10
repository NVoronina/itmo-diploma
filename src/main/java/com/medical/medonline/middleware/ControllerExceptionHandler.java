package com.medical.medonline.middleware;

import com.medical.medonline.dto.response.ErrorResponse;
import com.medical.medonline.exception.NotFoundException;
import com.medical.medonline.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(NotFoundException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> resourceValidationException(ValidationException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage(), ex.getCode()), HttpStatus.BAD_REQUEST);
    }

}