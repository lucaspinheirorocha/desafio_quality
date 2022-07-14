package com.github.transformeli.desafio_quality.handler;

import com.github.transformeli.desafio_quality.dto.ApiExceptionDTO;
import com.github.transformeli.desafio_quality.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionDTO> handlerApiException(ApiException ex)
    {
        return new ResponseEntity<>(ex.getDTO(), ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionDTO> handlerMethodException(MethodArgumentNotValidException exception)
    {
        ApiException ex = new ApiException(exception.getFieldError().getDefaultMessage());
        ex.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ex.getDTO(), ex.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiExceptionDTO> handlerHttpMessageNotReadable(HttpMessageNotReadableException exception)
    {
        ApiException ex = new ApiException(exception.getLocalizedMessage());
        ex.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ex.getDTO(), ex.getStatus());
    }

}
