package com.github.transformeli.desafio_quality.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.*;
import javax.validation.constraints.*;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @NotEmpty(message = "O nome da propriedade não pode estar vazio!")
    @Pattern(regexp = "([A-Z]|[0-9])[\\s|[0-9]|A-Z|a-z|ñ|ó|í|á|é|ú|Á|Ó|É|Í|Ú]*$", message = "O nome da propriedade deve iniciar com uma letra maíuscula!")
    @Size(max= 30, message = "O comprimento do nome não pode exceder trinta caracteres!")
    private String name;
    @NotEmpty(message = "A lista de cômodos deve ter pelo menos 1 válido.")
    private Set<@Valid Room> rooms;
    private Neighborhood neighborhood;
}
