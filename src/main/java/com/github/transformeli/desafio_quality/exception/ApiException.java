package com.github.transformeli.desafio_quality.exception;

import lombok.*;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.github.transformeli.desafio_quality.dto.ApiExceptionDTO;

@Data
public class ApiException extends RuntimeException {
    private String title;
    private HttpStatus status;
    private String messageData;
    private LocalDateTime timestamp;

    public ApiException(String message) {
        super(message);
        this.setStatus(HttpStatus.MULTI_STATUS);
        this.setMessageData(message);
        this.setTimestamp(LocalDateTime.now());
    }

    public void setStatus(HttpStatus statusData) {
        this.status = statusData;
        this.setTitle(statusData.getReasonPhrase());
    }

    public ApiExceptionDTO getDTO() {
        return ApiExceptionDTO.builder()
                .title(this.getTitle())
                .status(this.getStatus().value())
                .message(this.getMessageData())
                .timestamp(this.getTimestamp())
                .build();
    }
}
