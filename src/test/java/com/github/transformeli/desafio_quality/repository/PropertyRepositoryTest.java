package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.exception.PreconditionFailedException;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PropertyRepositoryTest {

    private PropertyRepository repo;
    private final String PROPERTY_NAME = "Casa do Emerson";
    private final String NEIGHBORHOOD = "Penha";
    final String EXISTENT_PROPERTY_NAME = "Casa do Isaias";
    final String PRECONDITION_ERROR_MESSAGE = "The property already exists.";

    @BeforeEach
    void setup() {
        repo = new PropertyRepository();
        Set<Property> threeProperties = TestUtilsProperty.buildThreeProperties();
        threeProperties.stream().forEach(property -> repo.create(property));
    }

    @Test
    void getKey() {
    }

    @DisplayName("Find All Properties on memory DB")
    @Test
    void findAll() {
        Property larisHouse = TestUtilsProperty.getNewProperty();

        Set<Property> returnedProperties = repo.findAll();

        assertThat(returnedProperties).isNotNull();
        assertThat(returnedProperties.size()).isPositive();
        assertThat(returnedProperties.contains(larisHouse));
    }

    @DisplayName("Find a property based on his primary key - propertyName")
    @Test
    void findByKey() {
        final String PROPERTY_NAME = "Casa do Isaias";

        Property foundProperty = repo.findByKey(PROPERTY_NAME);

        assertThat(foundProperty).isNotNull();
        assertThat(foundProperty.getName()).isEqualTo(PROPERTY_NAME);
    }

    @DisplayName("Return null when we search for a property that doesnt exists")
    @Test
    void findByKey_null() {
        final String PROPERTY_NAME = "Casa do Emerson";

        Property foundProperty = repo.findByKey(PROPERTY_NAME);

        assertThat(foundProperty).isNull();
    }

    @DisplayName("Create a property successfully when we provides name, neighborhood and roomsList correctly")
    @Test
    void create() {
        Set<Room> listOfRooms = new HashSet<>();
        listOfRooms.add(TestUtilsProperty.getNewRoom());
        Property emersonsHouse = TestUtilsProperty.buildProperty(PROPERTY_NAME, NEIGHBORHOOD, listOfRooms);

        Property createdProperty = repo.create(emersonsHouse);

        assertThat(createdProperty).isNotNull();
        assertThat(createdProperty.getName()).isEqualTo(emersonsHouse.getName());
//        assertThat(Arrays.stream(createdProperty.getClass().getFields())
//                .filter(f -> f.equals("name"))).isNotNull();
    }

    @DisplayName("Create method throws PreconditionException when we provides a property name that already exists")
    @Test
    void create_fail() {
        Set<Room> listOfRooms = new HashSet<>();
        listOfRooms.add(TestUtilsProperty.getNewRoom());
        Property emersonsHouse = TestUtilsProperty.buildProperty(EXISTENT_PROPERTY_NAME, NEIGHBORHOOD, listOfRooms);

        PreconditionFailedException exception = Assertions.assertThrows(
                PreconditionFailedException.class, () -> repo.create(emersonsHouse)
        );

        assertThat(exception).isNotNull();
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
        assertThat(exception.getMessage()).isEqualTo(PRECONDITION_ERROR_MESSAGE);
    }


    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
