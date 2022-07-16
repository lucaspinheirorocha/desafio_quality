package com.github.transformeli.desafio_quality.controller;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.PropertyDTO;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.service.IPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    @Autowired
    IPropertyService service;

    /**
     * Find all Properties to consume and calculate values
     *
     * @author Isaias Finger
     */
    @GetMapping("")
    public ResponseEntity<Set<PropertyDTO>> findAll() {
        Set<Property> allProperties = service.findAll();
        Set<PropertyDTO> result = new HashSet<>();
        allProperties.forEach(p -> result.add(service.propAreaCalculator(p)));
        if (result.isEmpty()) {
            throw new NotFoundException("any property was found");
        }
        return ResponseEntity.ok().body(result);
    }

    /**
     * Find one Property to consume and calculate values
     *
     * @param name
     * @author Isaias Finger
     */
    @GetMapping("/{name}")
    public ResponseEntity<PropertyDTO> findOne(@PathVariable String name) {
        Optional<Property> found = service.findByKey(name);
        if (found.isPresent()) {
            PropertyDTO result = service.propAreaCalculator(found.get());
            return ResponseEntity.ok().body(result);
        }
        throw new NotFoundException("property not found");
    }

    /**
     * Endpoint for create a new property
     *
     * @param property
     * @return ResponseEntity
     * @author Evelyn Cristini Oliveira / Alexandre Borges Souza
     */
    @PostMapping("")
    public ResponseEntity<Property> createNewNProperty(@RequestBody @Valid Property property) {
        Property result = service.createNewProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * Endpoint for update a new property
     *
     * @param property
     * @return ResponseEntity
     * @author Evelyn Cristini Oliveira / Alexandre Borges Souza
     */
    @PutMapping("/{name}")
    public ResponseEntity<Property> updateNProperty(@RequestBody @Valid Property property, @PathVariable String name) {
        Property result = service.updateProperty(name, property);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Endpoint for delete a new property
     *
     * @param name Name of Property
     * @return ResponseEntity
     * @author Evelyn Cristini Oliveira / Alexandre Borges Souza
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String name) {
        Boolean result = service.deleteProperty(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Endpoint for get price by neighborhood
     *
     * @param property
     * @return ResponseEntity
     * @author Evelyn Cristini Oliveira / Alexandre Borges Souza
     */
    @PostMapping("/neighborhoods/property-price-by-neighborhood")
    public ResponseEntity<Map<Property, Double>> propPriceByNeighborhood(@RequestBody @Valid Property property) {
        Double result = service.propPriceByNeighborhood(property);
        Map<Property, Double> body = new HashMap<>();
        body.put(property, result);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    /**
     * Endpoint for get max room by property
     *
     * @param property
     * @return ResponseEntity
     * @author Evelyn Cristini Oliveira / Alexandre Borges Souza
     */
    @PostMapping("/rooms/propBiggestRoom")
    public ResponseEntity<Room> propBiggestRoom(@RequestBody @Valid Property property) {
        Room propBiggestRoom = service.propBiggestRoom(property);
        return ResponseEntity.ok().body(propBiggestRoom);
    }
}
