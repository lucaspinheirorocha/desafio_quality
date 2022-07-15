package com.github.transformeli.desafio_quality.integration;


import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.repository.NeighborhoodRepository;
import com.github.transformeli.desafio_quality.util.TestUtilsNeighborhood;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NeighborhoodIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    NeighborhoodRepository repo;

    @LocalServerPort
    private int port;

    @Test
    public void registerNeighborhood_returnPreConditionFailed_whenNeighborhoodExists(){
        Neighborhood newNeighborhood = TestUtilsNeighborhood.findByName("Vila Matilde");
        repo.create(newNeighborhood);
        String baseUrl = "http://localhost:" + port + "/api/v1/property/neighborhood";
        HttpEntity<Neighborhood> httpEntity = new HttpEntity<>(newNeighborhood);

        ResponseEntity<Neighborhood> retorno = testRestTemplate
                .exchange(baseUrl, HttpMethod.POST, httpEntity, Neighborhood.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    public void registerNeighborhood_whenNeighborhoodDoesntExist(){
        Neighborhood newNeighborhood = TestUtilsNeighborhood.findByName("Jabotiana");
        String baseUrl = "http://localhost:" + port + "/api/v1/property/neighborhood";
        HttpEntity<Neighborhood> httpEntity = new HttpEntity<>(newNeighborhood);

        ResponseEntity<Neighborhood> retorno = testRestTemplate
                .exchange(baseUrl, HttpMethod.POST, httpEntity, Neighborhood.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(newNeighborhood).isNotNull();
        assertThat(newNeighborhood.getName()).isEqualTo(newNeighborhood.getName());
    }

    @Test
    public void getNeighborhood_returnStatusNotFound_whenNeighborhoodDoesntExist() {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        String baseUrl = "http://localhost:" + port + "/api/v1/property/neighborhood";

        ResponseEntity<Neighborhood> retorno = testRestTemplate
                .exchange(baseUrl + neighborhood.getName(), HttpMethod.GET, null, Neighborhood.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void modifyNeighborhood_returnStatusNotFound_whenNeighborhoodDoesntExist(){
        String baseUrl = "http://localhost:" + port + "/api/v1/property/neighborhood";

        Neighborhood newNeighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        NeighborhoodRepository dao = new NeighborhoodRepository();
        Neighborhood neighborhoodSaved = dao.create(newNeighborhood);

        neighborhoodSaved.setName("Novo bairro");
        HttpEntity<Neighborhood> httpEntity = new HttpEntity<>(neighborhoodSaved);

        ResponseEntity<Void> retorno = testRestTemplate.exchange(baseUrl, HttpMethod.PUT, httpEntity, void.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Optional<Neighborhood> neighborhoodFound = dao.findByKey(neighborhoodSaved.getName());
    }





}
