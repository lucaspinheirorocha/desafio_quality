package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Neighborhood;

import java.util.Optional;
import java.util.Set;

public interface INeighborhoodService {

    Set<Neighborhood> findAll();
    Optional<Neighborhood> findByKey(Optional<String> key);
    Neighborhood create(Neighborhood neighborhood);
    Neighborhood update(String key, Neighborhood neighborhood);
    Boolean delete(String name);
}
