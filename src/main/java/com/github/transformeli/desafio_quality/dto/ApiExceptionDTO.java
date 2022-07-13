package com.github.transformeli.desafio_quality.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
public class ApiExceptionDTO {
    private String title;
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
