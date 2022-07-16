package com.github.transformeli.desafio_quality.integration;


import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.repository.NeighborhoodRepository;
import com.github.transformeli.desafio_quality.util.TestUtilsNeighborhood;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NeighborhoodIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    NeighborhoodRepository repo;

    @LocalServerPort
    private int port;
    private String BASE_URL;

    @BeforeEach
    void setup() {
        BASE_URL = "http://localhost:" + port + "/api/v1/property/neighborhood";
    }

    @AfterEach
    void reset() {
        Neighborhood newNeighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        if (repo.findByKey(newNeighborhood.getName()).isPresent()) {
            repo.delete(newNeighborhood.getName());
        }
    }

    @Test
    public void registerNeighborhood_returnPreConditionFailed_whenNeighborhoodExists(){
        Neighborhood newNeighborhood = TestUtilsNeighborhood.findByName("Vila Matilde");
        repo.create(newNeighborhood);
        HttpEntity<Neighborhood> httpEntity = new HttpEntity<>(newNeighborhood);

        ResponseEntity<Neighborhood> retorno = testRestTemplate
                .exchange(BASE_URL, HttpMethod.POST, httpEntity, Neighborhood.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    public void registerNeighborhood_returnCreated_whenNeighborhoodDoesntExist(){
        Neighborhood newNeighborhood = TestUtilsNeighborhood.findByName("Jabotiana");
        HttpEntity<Neighborhood> httpEntity = new HttpEntity<>(newNeighborhood);

        ResponseEntity<Neighborhood> retorno = testRestTemplate
                .exchange(BASE_URL, HttpMethod.POST, httpEntity, Neighborhood.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(newNeighborhood).isNotNull();
        assertThat(newNeighborhood.getName()).isEqualTo(newNeighborhood.getName());
    }

    @Test
    public void getNeighborhood_returnStatusNotFound_whenNeighborhoodDoesntExist() {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();

        ResponseEntity<Neighborhood> retorno = testRestTemplate
                .exchange(BASE_URL + "/" + neighborhood.getName(), HttpMethod.GET, null, Neighborhood.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void modifyNeighborhood_returnStatusNotFound_whenNeighborhoodDoesntExist() {
        Neighborhood newNeighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        HttpEntity<Neighborhood> httpEntity = new HttpEntity<>(newNeighborhood);

        ResponseEntity<Void> retorno = testRestTemplate
                .exchange(BASE_URL + "/" + newNeighborhood.getName(), HttpMethod.PUT, httpEntity, Void.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void deleteNeighborhood_returnNotFound_whenNeighborhoodDoesntExist(){
        Neighborhood newNeighborhood = TestUtilsNeighborhood.findByName("Penha");
        HttpEntity<Neighborhood> httpEntity = new HttpEntity<>(newNeighborhood);

        ResponseEntity<Neighborhood> retorno = testRestTemplate
                .exchange(BASE_URL, HttpMethod.DELETE, httpEntity, Neighborhood.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getNeighborhood_returnOk_whenNeighborhoodExist(){
        Neighborhood newNeighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        repo.create(newNeighborhood);

        ResponseEntity<Neighborhood> retorno = testRestTemplate
                .exchange(BASE_URL + "/" + newNeighborhood.getName(), HttpMethod.GET, null, Neighborhood.class);

        assertThat(retorno.getBody().getName()).isEqualTo(newNeighborhood.getName());
        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void modifyNeighborhoodPrice_returnOk_whenNeighborhoodExists(){
        Neighborhood newNeighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        repo.create(newNeighborhood);
        Neighborhood updateNeighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        updateNeighborhood.setName("New Name Test");
        updateNeighborhood.setSqMeterPrice(100.0);
        HttpEntity<Neighborhood> httpEntity = new HttpEntity<>(updateNeighborhood);

        ResponseEntity<Neighborhood> retorno  = testRestTemplate
                .exchange(BASE_URL + "/" + newNeighborhood.getName(), HttpMethod.PUT, httpEntity, Neighborhood.class);

        assertThat(retorno.getBody().getName()).isEqualTo("New Name Test");
        assertThat(retorno.getBody().getSqMeterPrice()).isEqualTo(100.0);
        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteNeighborhood_returnOk_whenNeighborhoodExist(){
        Neighborhood newNeighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        repo.create(newNeighborhood);

        ResponseEntity<Void> retorno = testRestTemplate
                .exchange(BASE_URL + "/" + newNeighborhood.getName(), HttpMethod.DELETE, null, Void.class);

        assertThat(retorno.getBody()).isEqualTo(null);
        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
