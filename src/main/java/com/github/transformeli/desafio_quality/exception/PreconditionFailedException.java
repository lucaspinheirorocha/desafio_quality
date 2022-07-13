package com.github.transformeli.desafio_quality.exception;

import org.springframework.http.HttpStatus;

public class PreconditionFailedException extends ApiException{
    /**
     * PreconditionFailedException
     * @author laridevmeli
     * @param message to understand exception
     */
    public PreconditionFailedException(String message) {
        super(message);
        this.setStatus(HttpStatus.PRECONDITION_FAILED);
    }
}
