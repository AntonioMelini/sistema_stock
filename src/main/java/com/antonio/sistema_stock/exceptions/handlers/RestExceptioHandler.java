package com.antonio.sistema_stock.exceptions.handlers;

import com.antonio.sistema_stock.exceptions.api.ApiError;
import com.antonio.sistema_stock.exceptions.user.UserCreateValidation;
import com.antonio.sistema_stock.exceptions.user.UserNotFound;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class RestExceptioHandler {

    @ExceptionHandler(value = {UserNotFound.class})
    public ResponseEntity<ApiError> handleUserNotFoundException (UserNotFound e){
        System.out.println("entro a usernotfound");
        ApiError error = new ApiError(400,e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {UserCreateValidation.class})
    public ResponseEntity<ApiError> handleUserCreateValidationException (UserCreateValidation e){
        System.out.println(e);
        ApiError error = new ApiError(400,e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Map<String, String> handleConstraintViolationException (ConstraintViolationException e){
        System.out.println("entro a ConstraintViolationException");
        Map<String, String> errors = new HashMap<>();
        e.getConstraintViolations().forEach((error)-> {
            System.out.println(error.getMessage());
            String[] array=error.getMessage().split(" ");
            String fieldName = array[array.length-1];
            String message = error.getMessage();

            errors.put(fieldName,message);
        });
        return errors;
    }
    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ApiError> handleUsernameNotFoundException (UsernameNotFoundException e){
        ApiError error = new ApiError(400,e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }




}
