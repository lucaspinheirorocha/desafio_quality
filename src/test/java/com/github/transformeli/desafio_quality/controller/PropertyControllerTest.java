package com.github.transformeli.desafio_quality.controller;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.service.IPropertyService;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty;
import org.assertj.core.api.Assertions;
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
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PropertyControllerTest {

    @InjectMocks
    private PropertyController controller;

    @Mock
    IPropertyService service;

    @Test
    @DisplayName("Return successfully max room by property with contents")
    void propBiggestRoom_returnSuccessfully_whenWithPropertyContent() {
        BDDMockito.when(service.propBiggestRoom(ArgumentMatchers.any(Property.class)))
                .thenReturn(TestUtilsProperty.propBiggestRoom());

        Property property = TestUtilsProperty.getNewProperty();
        ResponseEntity<Room> response = controller.propBiggestRoom(property);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLength()).isPositive();
        assertThat(response.getBody().getWidth()).isPositive();
    }

    @Test
    @DisplayName("Return failed max room by without property contents")
    void propBiggestRoom_returnFailed_whenWithOutPropertyContent() {
        BDDMockito.when(service.propBiggestRoom(ArgumentMatchers.any(Property.class)))
                .thenReturn(TestUtilsProperty.propBiggestNoRoom());

        Property property = TestUtilsProperty.generateNewPropertyWhitoutContents();
        ResponseEntity<Room> response = controller.propBiggestRoom(property);
        assertTrue(response.getBody().getName() != null);
        verify(service, atLeastOnce()).propBiggestRoom(property);
    }

    @Test
    @DisplayName("Return success property price by neighborhood when property have contents")
    void propPriceByNeighborhood_returnSuccessfully_whenPropertyHaveContents() {
        BDDMockito.when(service.propPriceByNeighborhood(ArgumentMatchers.any(Property.class))).thenReturn(TestUtilsProperty.propPriceByNeighborhoodWithValue());

        Property property = TestUtilsProperty.getNewProperty();
        ResponseEntity<Map<Property, Double>> response = controller.propPriceByNeighborhood(property);
        assertThat(response.getBody()).isNotNull();
        assertFalse(response.getBody().entrySet().isEmpty());
    }

    @Test
    @DisplayName("Return faliure property price by neighborhood when property not have contents")
    void propPriceByNeighborhood_returnSuccessfully_whenPropertyNotHaveContents() throws NotFoundException {
        BDDMockito.when(service.propPriceByNeighborhood(ArgumentMatchers.any(Property.class))).thenReturn(TestUtilsProperty.propPriceByNeighborhoodWithoutValue());
        Property property = TestUtilsProperty.generateNewPropertyWhitoutContents();
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException("Room not found.");
        });
        assertEquals("Room not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Return successfully created a new property")
    public void createProperty_returnSuccessfully_whenWithPropertyContent() {
        BDDMockito.when(service.createNewProperty(ArgumentMatchers.any(Property.class))).thenReturn((TestUtilsProperty.getNewProperty()));
        Property newProperty = TestUtilsProperty.getNewProperty();
        ResponseEntity<Property> response = controller.createNewNProperty(newProperty);
        assertThat(response.getBody()).isInstanceOf(Property.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRooms().size())
                .isNotNull()
                .isPositive();
        verify(service, atLeastOnce()).createNewProperty(newProperty);
    }

    @Test
    @DisplayName("Return failed created a new property")
    public void createProperty_returnFailed_whenPropertyNoContent() {
        BDDMockito.when(service.createNewProperty(ArgumentMatchers.any(Property.class))).thenReturn((TestUtilsProperty.generateNewPropertyWhitoutContents()));

        Property newProperty = TestUtilsProperty.generateNewPropertyWhitoutContents();
        ResponseEntity<Property> response = controller.createNewNProperty(newProperty);
        assertTrue(response.getBody().getName().isEmpty());
        assertFalse(response.getBody().getRooms() != null);
        assertFalse(response.getBody().getNeighborhood() != null);
    }

    @Test
    @DisplayName("Return successfully updated property")
    public void updateProperty_returnSuccessfully_whenWithPropertyContent() {
        BDDMockito.when(service.updateProperty(ArgumentMatchers.anyString(), ArgumentMatchers.any(Property.class)))
                .thenReturn(TestUtilsProperty.getNewProperty());
        Property newProperty = TestUtilsProperty.getNewProperty();

        ResponseEntity<Property> response = controller.updateNProperty(newProperty, newProperty.getName());

        Assertions.assertThat(response.getBody()).isInstanceOf(Property.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRooms().size())
                .isNotNull()
                .isPositive();
        verify(service, atLeastOnce()).updateProperty(newProperty.getName(), newProperty);
    }

    @Test
    @DisplayName("Return failed updated property")
    public void updateProperty_returnFailed_whenWithoutPropertyContent() {
        BDDMockito.when(service.updateProperty(ArgumentMatchers.anyString(), ArgumentMatchers.any(Property.class)))
                .thenThrow(new NotFoundException("The property not exists."));
        Property newProperty = TestUtilsProperty.getNewProperty();

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            controller.updateNProperty(newProperty, newProperty.getName());
        });

        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Return successfully delete property")
    void deleteProperty_returnSuccessfully_whenWithPropertyContent() {
        BDDMockito.when(service.deleteProperty(ArgumentMatchers.anyString())).thenReturn(true);
        String name = TestUtilsProperty.getNewProperty().getName().toString();

        ResponseEntity<Void> response = controller.deleteProperty(name);

        verify(service, atLeastOnce()).deleteProperty(name);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Return successfully delete property")
    void deleteProperty_returnFalied_whenWithoutPropertyContent() {
        BDDMockito.when(service.deleteProperty(ArgumentMatchers.anyString()))
                .thenThrow(new NotFoundException("The property not exists."));
        String name = "Mansão do Faustão";

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            controller.deleteProperty(name);
        });

        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
