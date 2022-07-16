package com.github.transformeli.desafio_quality.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private String name;
    private Double length;
    private Double width;
    private Double roomArea;
}
