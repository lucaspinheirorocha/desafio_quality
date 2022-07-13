package com.github.transformeli.desafio_quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {
    private Long id;
    private String name;

}
