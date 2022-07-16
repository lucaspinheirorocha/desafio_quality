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

    /**
     * Search for a neighborhood by its name
     * @param name name of neighborhood
     * @return
     */
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

    /**
     * Create a brand new neighborhood
     * @param neighborhood a instance of Neighborhood Class
     * @return
     */
    public Neighborhood create(Neighborhood neighborhood) {
        return repo.create(neighborhood);
    }

    /**
     *  Util for use on update tests
     * @param key neighborhood name
     * @param neighborhood neighborhood to update DB
     * @author Lucas Pinheiro
     * @return
     */
    public Neighborhood update(String key, Neighborhood neighborhood) {
        Optional<Neighborhood> neighborhoodFound = repo.findByKey(key);
        if(neighborhoodFound.isPresent())
        {
            return repo.update(key, neighborhood);
        }
        throw new NotFoundException("neighborhood not found");
    }

    /**
     * Deletes a neighborhood given an existent property
     * @param name name of a property
     * @return
     */
    public Boolean delete(String name) {
        return repo.delete(name);
    }

}
