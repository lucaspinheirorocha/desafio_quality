package com.github.transformeli.desafio_quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Neighborhood {
    @NotBlank(message = " O nome do bairro não pode estar vazio.")
    @Size(max = 45, message = "O comprimento do nome do bairro não pode exceder 45 caracteres.")
    private String name;
    @NotBlank(message = "O valor do metro quadrado do bairro não pode estar vazio.")
    @DecimalMax(value = "13")
    private Double sqMeterPrice;


}
