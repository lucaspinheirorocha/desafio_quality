package com.github.transformeli.desafio_quality.handler;

import com.github.transformeli.desafio_quality.dto.ApiExceptionDTO;
import com.github.transformeli.desafio_quality.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionDTO> handlerApiException(ApiException ex)
    {
        return new ResponseEntity<>(ex.getDTO(), ex.getStatus());
    }

}
