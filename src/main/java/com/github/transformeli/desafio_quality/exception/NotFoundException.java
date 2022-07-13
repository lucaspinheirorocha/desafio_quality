package com.github.transformeli.desafio_quality.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    public NotFoundException(String message) {
        super(message);
        this.setStatus(HttpStatus.NOT_FOUND);
    }

}
