package com.insurance_domain.exceptation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GlobalExceptionHandler {

    @ExceptionHandler(NoRecordFoundException.class)
    public ResponseEntity<String>  noRecordFoundException(NoRecordFoundException noRecordFoundException){

        ResponseEntity<String> stringResponseEntity = new ResponseEntity<>("no such record", HttpStatus.NOT_FOUND);
        return stringResponseEntity;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> globalException(Exception e){
        ResponseEntity<String> notFound = new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        return notFound;
    }
}
