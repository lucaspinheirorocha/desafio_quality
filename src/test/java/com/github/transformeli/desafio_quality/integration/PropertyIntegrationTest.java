package com.github.transformeli.desafio_quality.integration;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.repository.PropertyRepository;
import com.github.transformeli.desafio_quality.util.TestUtilsProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    PropertyRepository repo;

    @LocalServerPort
    private int port;
    private String BASE_URL;

    @BeforeEach
    void setup() {
        BASE_URL = "http://localhost:" + port + "/api/v1/property";
    }

    @DisplayName("Register property when the property exists")
    @Test
    public void registerProperty_returnPreConditionFailed_whenPropertyExists() {
        Set<Room> listOfRooms = new HashSet<>();
        listOfRooms.add(TestUtilsProperty.getNewRoom());
        Property evelynHouse = TestUtilsProperty.buildProperty("Evelyns House", "Penha", listOfRooms);
        repo.create(evelynHouse);
        HttpEntity<Property> httpEntity = new HttpEntity<>(evelynHouse);

        ResponseEntity<Property> response = testRestTemplate
                .exchange(BASE_URL, HttpMethod.POST, httpEntity, Property.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @DisplayName("Register property when the property doesn't exists")
    @Test
    public void registerProperty_whenPropertyDoesntExists(){
        Property newProperty = TestUtilsProperty.getNewProperty();
        HttpEntity<Property> httpEntity = new HttpEntity<>(newProperty);

        ResponseEntity<Property> response = testRestTemplate
                .exchange(BASE_URL, HttpMethod.POST, httpEntity, Property.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(newProperty).isNotNull();
        assertThat(newProperty.getName()).isEqualTo(newProperty.getName());
    }

    @DisplayName("Get property when property doesn't exists")
    @Test
    public void getProperty_returnStatusNotFound_whenPropertyDoesntExists() {
        Property property = TestUtilsProperty.getNewProperty();

        ResponseEntity<Property> response = testRestTemplate
                .exchange(BASE_URL + "/" + property.getName(), HttpMethod.GET, null, Property.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @DisplayName("Modify property when property doesn't exist")
    @Test
    public void modifyProperty_returnStatusNotFound_whenPropertyDoesntExist(){
        Property newProperty = TestUtilsProperty.getNewProperty();
        PropertyRepository dao = new PropertyRepository();
        Property propertySaved = dao.create(newProperty);

        propertySaved.setName("Nova propriedade");
        HttpEntity<Property> httpEntity = new HttpEntity<>(propertySaved);
        ResponseEntity<Void> response = testRestTemplate
                .exchange(BASE_URL, HttpMethod.PUT, httpEntity, void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Optional<Property> propertyFound =  dao.findByKey(propertySaved.getName());
    }
}
