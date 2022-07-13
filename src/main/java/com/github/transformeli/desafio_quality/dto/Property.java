package com.github.transformeli.desafio_quality.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @NotBlank(message = "O nome da propriedade não pode estar vazio!")
    @Pattern(regexp = "\"([A-Z]|[0-9])[\\\\s|[0-9]|A-Z|a-z|ñ|ó|í|á|é|ú|Á|Ó|É|Í|Ú]*$\"", message = "O nome da propriedade deve comecar com uma letra maíuscula!")
    @Size(max= 30, message = "O comprimento do nome não pode exceder trinta caracteres!")
    private String name;
    private Set<@Valid Room> room;
    @NotBlank(message = "O bairro não pode estar vazio!")
    @Size(max = 45,message = "O comprimento do bairro não pode exceder 45 caracteres!")
    private Neighborhood neighborhood;

    public double propertyDimensions(){
        return 0.0;
    }
    public double totalCost(){
        return 0.0;
    }

}
