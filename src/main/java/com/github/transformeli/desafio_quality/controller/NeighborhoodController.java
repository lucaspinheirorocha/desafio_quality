package com.github.transformeli.desafio_quality.controller;

import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.service.INeighborhoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1/property/neighborhood")
public class NeighborhoodController {

    @Autowired
    INeighborhoodService service;

    @GetMapping("")
    public ResponseEntity<Set<Neighborhood>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Neighborhood> findOne(@PathVariable Optional<String> name) {
        if(name.isPresent())
        {
            Optional<Neighborhood> result = service.findByKey(name);
            return ResponseEntity.ok(result.get());
        }
        throw new NotFoundException("neighborhood not found");
    }

    @PostMapping("")
    public ResponseEntity<Neighborhood> create(@RequestBody @Valid Neighborhood neighborhood) {
        Neighborhood result = service.create(neighborhood);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Neighborhood> update(@RequestBody @Valid Neighborhood neighborhood, @PathVariable String name) {
        Neighborhood result = service.update(name, neighborhood);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        service.delete(name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
