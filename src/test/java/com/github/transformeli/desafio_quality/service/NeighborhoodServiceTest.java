package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.repository.NeighborhoodRepository;
import com.github.transformeli.desafio_quality.util.TestUtilsNeighborhood;
import org.junit.jupiter.api.Assertions;
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

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class NeighborhoodServiceTest {

    @InjectMocks
    NeighborhoodService service;

    @Mock
    NeighborhoodRepository repository;

    @BeforeEach
    public void setup() {
        BDDMockito
                .when(repository.findAll())
                .thenReturn(TestUtilsNeighborhood.getSetOfNeighborhood());

        BDDMockito
                .when(repository.findByKey(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(TestUtilsNeighborhood.getNewNeighborhood()));

        BDDMockito
                .when(repository.create(ArgumentMatchers.any(Neighborhood.class)))
                .thenReturn(TestUtilsNeighborhood.getNewNeighborhood());
    }

    @DisplayName("Find All properties that exists on DB")
    @Test
    void findAll() {
        Set<Neighborhood> allNeighborhoods = service.findAll();

        verify(repository, atLeastOnce()).findAll();

        assertThat(allNeighborhoods.size()).isPositive();
        assertThat(allNeighborhoods.isEmpty()).isFalse();
    }

    @DisplayName("Find By Key: When neighborhood exists")
    @Test
    void findByKey() {
        Optional<String> neighborhoodName = Optional.of("Bela Vista");

        Optional<Neighborhood> foundNeighborhood = service.findByKey(neighborhoodName);

        assertThat(foundNeighborhood.get().getName()).isEqualTo(neighborhoodName.get());
    }

    @DisplayName("Find By Key: When neighborhood does not exists")
    @Test
    void findByKey_exception() {
        Optional<String> neighborhoodName = Optional.of("batatinha");
        BDDMockito
                .when(repository.findByKey(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsNeighborhood.findByKeyException());

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class, () -> service.findByKey(neighborhoodName)
        );

        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @DisplayName("Create: Create a neighborhood successfully when provides a correct neighborhood")
    @Test
    void create() {
        Neighborhood newNeighborhood = TestUtilsNeighborhood.getNewNeighborhood();

        Neighborhood createdNeighborhood = service.create(newNeighborhood);

        assertThat(createdNeighborhood.getName()).isEqualTo(newNeighborhood.getName());
        assertThat(createdNeighborhood).isNotNull();
    }

    @DisplayName("Update: Throws a NotFoundException when provides a neighborhood that doesnt exists")
    @Test
    void update_exception() {
        String oldNeighborhoodName = "Casa Branca";
        Neighborhood newNeighborhood = TestUtilsNeighborhood.buildNeighborhood("Batatinha", 82D);
        BDDMockito
                .when(repository.findByKey(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsNeighborhood.findByKeyException());

        NotFoundException exception = Assertions.assertThrows(
                NotFoundException.class, () -> service.update(oldNeighborhoodName, newNeighborhood)
        );

        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @DisplayName("Update: Update a neighborhood successfully when provides a neighborhood that exists")
    @Test
    void update() {
        String oldNeighborhoodName = "Penha";
        Neighborhood newNeighborhood = TestUtilsNeighborhood.buildNeighborhood("Batatinha", 82D);
        BDDMockito
                .when(repository.update(ArgumentMatchers.anyString(), ArgumentMatchers.any(Neighborhood.class)))
                .thenReturn(TestUtilsNeighborhood
                        .updateNeighborhood(oldNeighborhoodName, newNeighborhood));

        Neighborhood updatedNeighborhood = service.update(oldNeighborhoodName, newNeighborhood);

        assertThat(updatedNeighborhood).isNotNull();
        assertThat(updatedNeighborhood.getName()).isEqualTo(newNeighborhood.getName());
    }

    @DisplayName("Delete: delete a neighborhood successfully when neighborhood exists")
    @Test
    void delete() {
        String neighborhoodName = "Penha";
        BDDMockito
                .when(repository.delete(ArgumentMatchers.anyString()))
                .thenReturn(TestUtilsNeighborhood.deleteUtil(neighborhoodName));

        Boolean isNeighborhoodDeleted = service.delete(neighborhoodName);

        assertThat(isNeighborhoodDeleted).isTrue();
    }
}
