package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.exception.PreconditionFailedException;
import com.github.transformeli.desafio_quality.util.TestUtilsNeighborhood;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;


class NeighborhoodRepositoryTest {

    private NeighborhoodRepository repo;

    @BeforeEach
    void setup()
    {
        repo = new NeighborhoodRepository();
    }

    @Test
    @DisplayName("Find By Key when Neighborhood exists")
    void findByKey() {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        Neighborhood neighborhoodSaved = repo.create(neighborhood);
        assertThat(neighborhoodSaved.getName()).isEqualTo(neighborhood.getName());

        Neighborhood result = repo.findByKey(neighborhood.getName().toUpperCase()).get();

        assertThat(result.getName()).isEqualTo(neighborhoodSaved.getName());
        assertThat(result.getSqMeterPrice()).isEqualTo(neighborhoodSaved.getSqMeterPrice());
    }

    @Test
    @DisplayName("Find By Key when Neighborhood Not exists")
    void findByKey_whenNotExists() {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();

        Optional<Neighborhood> result = repo.findByKey(neighborhood.getName().toUpperCase());

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Find All")
    void findAll() {
        Set<Neighborhood> list = TestUtilsNeighborhood.getSetOfNeighborhood();
        list.forEach(n -> repo.create(n));

        Set<Neighborhood> result = repo.findAll();

        assertThat(list.size()).isEqualTo(result.size());
    }

    @Test
    @DisplayName("Create a Neighborhood")
    void create() {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();

        Neighborhood saved = repo.create(neighborhood);

        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo(neighborhood.getName());
    }

    @Test
    @DisplayName("Fail when Neighborhood already exists")
    void create_throwPreconditionFailed_whenNeighborhoodAlreadyExists() {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        Neighborhood neighborhoodSaved = repo.create(neighborhood);
        assertThat(neighborhoodSaved).isNotNull();
        assertThat(neighborhoodSaved.getName()).isEqualTo(neighborhood.getName());

        PreconditionFailedException ex = Assertions.assertThrows(PreconditionFailedException.class, () -> {
            Neighborhood failed = repo.create(neighborhood);
        });

        assertThat(ex.getStatus()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    @DisplayName("Update exists Neighborhood")
    void update() {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        Double oldSqMeterPrice = neighborhood.getSqMeterPrice();
        Neighborhood created = repo.create(neighborhood);
        assertThat(created.getName()).isEqualTo(neighborhood.getName());
        assertThat(created.getSqMeterPrice()).isEqualTo(neighborhood.getSqMeterPrice());

        Double newSqMeterPrice = oldSqMeterPrice / 2;
        neighborhood.setSqMeterPrice(newSqMeterPrice);
        repo.update(neighborhood.getName(), neighborhood);

        Neighborhood updated = repo.findByKey(neighborhood.getName()).get();
        assertThat(updated.getSqMeterPrice()).isNotEqualTo(oldSqMeterPrice);
        assertThat(updated.getSqMeterPrice()).isEqualTo(newSqMeterPrice);
    }

    @Test
    @DisplayName("Update Neighborhood when Not exists")
    void update_whenNotExists()
    {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();

        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            repo.update(neighborhood.getName(), neighborhood);
        });

        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Delete Neighborhood when exists")
    void delete() {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();
        repo.create(neighborhood);

        Boolean hasDeleted = repo.delete(neighborhood.getName());

        assertThat(hasDeleted).isTrue();
    }

    @Test
    @DisplayName("Delete Neighborhood when Not exists")
    void delete_whenNotExists()
    {
        Neighborhood neighborhood = TestUtilsNeighborhood.getNewNeighborhood();

        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            repo.delete(neighborhood.getName());
        });

        assertThat(ex.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
