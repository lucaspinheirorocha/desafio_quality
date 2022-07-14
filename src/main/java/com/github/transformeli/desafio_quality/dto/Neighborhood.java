package com.github.transformeli.desafio_quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Neighborhood {
    @NotEmpty(message = "O nome do bairro não pode estar vazio.")
    @Size(max = 45, message = "O comprimento do nome do bairro não pode exceder 45 caracteres.")
    private String name;
    @NotNull(message = "O valor do metro quadrado do bairro não pode estar vazio.")
    @DecimalMin(value = "1", message = "O valor do metro quadrado deve ter no mínimo 1 digito.")
    @DecimalMax(value = "9999999999999", message = "O valor do metro quadrado deve ter no máximo 13 digitos.")
    private Double sqMeterPrice;
}
