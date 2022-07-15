package com.github.transformeli.desafio_quality.controller;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.service.IPropertyService;
import com.github.transformeli.desafio_quality.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    @Autowired
    IPropertyService service;

    /**
     * Endpoint for create a new property
     *
     * @author  Evelyn Cristini Oliveira / Alexandre Borges Souza
     * @param property
     * @return ResponseEntity
     */
    @PostMapping("")
    public ResponseEntity<Property> createNewNProperty(@RequestBody @Valid Property property){
        Property result =  service.createNewProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * Endpoint for update a new property
     *
     * @author  Evelyn Cristini Oliveira / Alexandre Borges Souza
     * @param property
     * @return ResponseEntity
     */
    @PutMapping("/{name}")
    public ResponseEntity<Property> updateNProperty(@RequestBody @Valid Property property, @PathVariable String name){
        Property result =  service.updateProperty(name, property);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Endpoint for delete a new property
     *
     * @author  Evelyn Cristini Oliveira / Alexandre Borges Souza
     * @param property
     * @return ResponseEntity
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String name) {
        Boolean result = service.deleteProperty(name);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Endpoint for get price by neighborhood
     *
     * @author  Evelyn Cristini Oliveira / Alexandre Borges Souza
     * @param property
     * @return ResponseEntity
     */
    @PostMapping("/neighborhoods/property-price-by-neighborhood")
    public ResponseEntity<Map<Property, Double>> propPriceByNeighborhood (@RequestBody @Valid Property property) {
        Double result = service.propPriceByNeighborhood(property);
        Map<Property, Double> body = new HashMap<>();
        body.put(property, result);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    /**
     * Endpoint for get max room by property
     *
     * @author  Evelyn Cristini Oliveira / Alexandre Borges Souza
     * @param property
     * @return ResponseEntity
     */
    @PostMapping("/rooms/propBiggestRoom")
    public ResponseEntity<Room> propBiggestRoom(@RequestBody @Valid Property property) {
        Room propBiggestRoom = service.propBiggestRoom(property);
        return  ResponseEntity.ok().body(propBiggestRoom);
    }
}
