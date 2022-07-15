package com.github.transformeli.desafio_quality.exception;

import org.springframework.http.HttpStatus;

public class NullPointerException extends ApiException{
    public NullPointerException(String message) {
        super(message);
        this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
