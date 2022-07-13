package com.github.transformeli.desafio_quality.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
    /**
     * BadRequestException
     * @author Isaias
     * @param message to understand exception
     */
    public BadRequestException(String message) {
        super(message);
        this.setStatus(HttpStatus.BAD_REQUEST);
    }

}
