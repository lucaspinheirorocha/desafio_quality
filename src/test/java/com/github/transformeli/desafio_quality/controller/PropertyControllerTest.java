package com.github.transformeli.desafio_quality.controller;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.service.IPropertyService;
import com.github.transformeli.desafio_quality.service.PropertyService;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PropertyControllerTest {

    @InjectMocks
    private PropertyController controller;
    @Mock
    IPropertyService service;

    @BeforeEach
    public void setup() {
        BDDMockito.when(service.propBiggestRoom(ArgumentMatchers.any(Property.class))).thenReturn(TestUtilsProperty2.propBiggestRoom());
    }

    @Test
    @DisplayName("Search for the biggest room inside the property")
    public void propBiggestRoom() {
        Property property = TestUtilsProperty2.getNewProperty();
        ResponseEntity<Room> response = controller.propBiggestRoom(property);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLength()).isPositive();
        assertThat(response.getBody().getWidth()).isPositive();
    }

    
}
