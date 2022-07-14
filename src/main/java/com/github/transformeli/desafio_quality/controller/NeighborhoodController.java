package com.github.transformeli.desafio_quality.controller;

import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.service.NeighborhoodService;
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
    NeighborhoodService service;

    @GetMapping("")
    public ResponseEntity<Set<Neighborhood>> findAll(@RequestParam Optional<String> name) {
        if(name.isPresent())
        {
            Optional<Neighborhood> result = service.findByKey(name);
            return ResponseEntity.ok(Collections.singleton(result.get()));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("")
    public ResponseEntity<Neighborhood> create(@RequestBody @Valid Neighborhood neighborhood) {
        Neighborhood result = service.create(neighborhood);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("")
    public ResponseEntity<Neighborhood> update(@RequestBody @Valid Neighborhood neighborhood) {
        Neighborhood result = service.update(neighborhood);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestParam Optional<String> name) {
        Optional<Neighborhood> result = service.findByKey(name);
        if (result.isPresent()) {
            service.delete(result.get());
        } else {
            throw new NotFoundException("neighborhood not found");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
