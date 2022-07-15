package com.github.transformeli.desafio_quality.exception;

import org.springframework.http.HttpStatus;

public class ErrorPropertyRequestException extends ApiException {
    /**
     * InvalidPropertyException
     * @author Alexandre Borges Souza <alexadre.bsouza@mercadolivre.com>
     * @param message to understand exception
     */
    public ErrorPropertyRequestException(String message) {
        super(message);
        this.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
