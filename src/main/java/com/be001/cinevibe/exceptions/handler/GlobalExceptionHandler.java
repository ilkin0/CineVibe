package com.be001.cinevibe.exceptions.handler;

import com.be001.cinevibe.dto.ExceptionResponseDTO;
import com.be001.cinevibe.exception.AlreadyExistsException;
import com.be001.cinevibe.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoDataFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ExceptionResponseDTO handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return new ExceptionResponseDTO(HttpStatus.NOT_FOUND.value(), noDataFoundException.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ExceptionResponseDTO handleAlreadyExistsException(AlreadyExistsException alreadyExistsException){
        return new ExceptionResponseDTO(HttpStatus.CONFLICT.value(), alreadyExistsException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleMethodArgumentInvalid(MethodArgumentNotValidException exception) {
        Map<String, List<String>> errors = new ConcurrentHashMap<>();
        exception.getBindingResult().getGlobalErrors().forEach(globalError ->
                errors.computeIfAbsent(globalError.getObjectName(), k -> new ArrayList<>())
                        .add(globalError.getDefaultMessage())
        );

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>())
                    .add(fieldError.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
