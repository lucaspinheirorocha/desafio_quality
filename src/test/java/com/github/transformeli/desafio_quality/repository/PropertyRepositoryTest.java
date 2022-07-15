package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.exception.PreconditionFailedException;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PropertyRepositoryTest {

    private PropertyRepository repo;
    private final String PROPERTY_NAME = "Casa do Emerson";
    private final String LARI_HOUSE_NAME_TRIMMED = "casadalari";
    private final String NEIGHBORHOOD = "Penha";
    final String EXISTENT_PROPERTY_NAME = "Casa do Isaias";
    final String PRECONDITION_ERROR_MESSAGE = "The property already exists.";
    final String NOT_FOUND_MESSAGE = "The property not exists.";

    @BeforeEach
    void setup() {
        repo = new PropertyRepository();
        Set<Property> threeProperties = TestUtilsProperty.buildThreeProperties();
        threeProperties.stream().forEach(property -> repo.create(property));
    }

    @DisplayName("Get the primary key from a property - propertyName -")
    @Test
    void getKey() {
        Property larisHouse = TestUtilsProperty.getNewProperty();
        String keyName = repo.getKey(larisHouse);

        assertThat(keyName).isEqualTo(LARI_HOUSE_NAME_TRIMMED);
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

        Property foundProperty = repo.findByKey(PROPERTY_NAME).get();

        assertThat(foundProperty).isNotNull();
        assertThat(foundProperty.getName()).isEqualTo(PROPERTY_NAME);
    }

    @DisplayName("Return null when we search for a property that doesnt exists")
    @Test
    void findByKey_null() {
        final String PROPERTY_NAME = "Casa do Emerson";

        Optional<Property> foundProperty = repo.findByKey(PROPERTY_NAME);

        assertThat(foundProperty.isEmpty()).isTrue();
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


    @DisplayName("Update: a property is successfully updated when we provides an existent property")
    @Test
    void update() {
        Set<Room> listOfRooms = new HashSet<>();
        listOfRooms.add(TestUtilsProperty.getNewRoom());
        Property emersonsHouse = TestUtilsProperty.buildProperty(PROPERTY_NAME, NEIGHBORHOOD, listOfRooms);
        Property savedHouse = repo.create(emersonsHouse);
        savedHouse.setRooms(TestUtilsProperty.buildRooms());

        Property updatedProperty = repo.update(savedHouse);

        assertThat(updatedProperty).isNotNull();
        assertThat(updatedProperty.getRooms().size())
                .isEqualTo(savedHouse.getRooms().size());
    }

//    @Test
//    void update_bug_name() {
//        Set<Room> listOfRooms = new HashSet<>();
//        listOfRooms.add(TestUtilsProperty.getNewRoom());
//        Property emersonsHouse = TestUtilsProperty.buildProperty(PROPERTY_NAME, NEIGHBORHOOD, listOfRooms);
//        Property savedHouse = repo.create(emersonsHouse);
//        savedHouse.setName("Emerson nao mora mais aqui");
//
//        Property updatedProperty = repo.update(savedHouse);
//
//        assertThat(updatedProperty).isNotNull();
//        assertThat(updatedProperty.getName()).isEqualTo(savedHouse.getName());
//    }

    @DisplayName("Update: throws a NotFoundException when we provides a nonexistent property")
    @Test
    void update_exception() {
        Set<Room> listOfRooms = new HashSet<>();
        listOfRooms.add(TestUtilsProperty.getNewRoom());
        Property emersonsHouse = TestUtilsProperty.buildProperty(PROPERTY_NAME, NEIGHBORHOOD, listOfRooms);

        emersonsHouse.setName("Emerson nao mora mais aqui");

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class, () -> repo.update(emersonsHouse)
        );

        assertThat(exception).isNotNull();
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND_MESSAGE);
    }

    @DisplayName("Delete: a property is successfully deleted when we provides an existent property")
    @Test
    void delete() {
        Property larisHouse = TestUtilsProperty.getNewProperty();

        Boolean deletedProperty = repo.delete(larisHouse);

        assertThat(deletedProperty).isTrue();
    }

    @DisplayName("Delete: a property is successfully deleted when we provides an existent property")
    @Test
    void delete_exception() {
        Set<Room> listOfRooms = new HashSet<>();
        listOfRooms.add(TestUtilsProperty.getNewRoom());
        Property emersonsHouse = TestUtilsProperty.buildProperty(PROPERTY_NAME, NEIGHBORHOOD, listOfRooms);

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class, () -> repo.delete(emersonsHouse)
        );

        assertThat(exception).isNotNull();
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND_MESSAGE);
    }

}
