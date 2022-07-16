package com.github.transformeli.desafio_quality.dto;

import lombok.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {
    private String name;
    private Set<RoomDTO> rooms;
    private Neighborhood neighborhood;
    private RoomDTO biggestRoom;
    private Double propertyArea;
    private Double propertyPrice;
}
