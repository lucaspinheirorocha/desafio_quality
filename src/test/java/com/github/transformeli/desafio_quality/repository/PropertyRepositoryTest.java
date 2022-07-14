package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PropertyRepositoryTest {

    private PropertyRepository repo;

    @BeforeEach
    void setup() {
        repo = new PropertyRepository();
    }

    @Test
    void getKey() {
    }

    @Test
    void findAll() {
        Set<Property> allProperties = TestUtilsProperty.buildThreeProperties();
        allProperties.stream().forEach(property -> repo.create(property));

        Set<Property> returnedProperties = repo.findAll();

        assertThat(returnedProperties).isNotNull();
        assertThat(returnedProperties.size()).isEqualTo(allProperties.size());
    }

    @Test
    void findByKey() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
