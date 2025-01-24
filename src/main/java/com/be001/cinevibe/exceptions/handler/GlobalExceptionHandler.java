package com.be001.cinevibe.exceptions.handler;

import com.be001.cinevibe.exceptions.NoDataFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoDataFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoDataFoundException(NoDataFound noDataFound) {
        return ResponseEntity.ok(noDataFound.getMessage());
    }

}
