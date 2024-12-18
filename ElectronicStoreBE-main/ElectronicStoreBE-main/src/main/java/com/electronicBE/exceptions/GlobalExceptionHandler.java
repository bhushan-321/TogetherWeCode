package com.electronicBE.exceptions;

import com.electronicBE.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // handler resource not found exception

    private Logger logger = LoggerFactory.getLogger((GlobalExceptionHandler.class));

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> ResourceNotFOundExceptionHandler(ResourceNotFoundException ex) {

        logger.info("Exception Handler invoked !!");

        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).httpStatus(HttpStatus.NOT_FOUND).success(true).build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

    // MethodArgumentNotValidException

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> MethodArgumentExceptionHandler(MethodArgumentNotValidException ex) {

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();
        allErrors.stream().forEach(o -> {

            String defaultMessage = o.getDefaultMessage();
            String field = ((FieldError) o).getField();

            response.put(field, defaultMessage);


        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(BadApiException.class)
    public ResponseEntity<ApiResponseMessage> HandleBadApiException(BadApiException ex){

        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(ex.getMessage()).success(false).httpStatus(HttpStatus.BAD_REQUEST).build();

        return  new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UserFoundException.class)
    public ResponseEntity<ApiResponseMessage>UserFoundException(UserFoundException e){
        ApiResponseMessage build = ApiResponseMessage.builder().message(e.getMessage()).success(false).httpStatus(HttpStatus.ALREADY_REPORTED).build();
        return  new ResponseEntity<>(build,HttpStatus.BAD_REQUEST);
    }



}
