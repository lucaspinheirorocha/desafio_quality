package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.repository.ICrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class NeighborhoodService implements INeighborhoodService {

    @Autowired
    ICrud<Neighborhood> repo;

    public Set<Neighborhood> findAll() {
        return repo.findAll();
    }

    public Optional<Neighborhood> findByKey(Optional<String> name) {
        Optional<Neighborhood> result = Optional.empty();
        if (name.isPresent()) {
            result = repo.findByKey(name.get());
            if (result.isEmpty()) {
                throw new NotFoundException("neighborhood not found");
            }
        }
        return result;
    }

    public Neighborhood create(Neighborhood neighborhood) {
        return repo.create(neighborhood);
    }

    public Neighborhood update(Neighborhood neighborhood) {
        return repo.update(neighborhood);
    }

    public Boolean delete(Neighborhood neighborhood) {
        return repo.delete(neighborhood);
    }

}
