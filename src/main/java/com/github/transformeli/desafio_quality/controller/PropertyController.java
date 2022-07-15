package com.github.transformeli.desafio_quality.controller;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.service.IPropertyService;
import com.github.transformeli.desafio_quality.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private IPropertyService service = new PropertyService();

    @PostMapping("")
    public ResponseEntity<Property> createNewNProperty(@RequestBody @Valid Property property){
        Property result =  service.createNewProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("")
    public ResponseEntity<Property> updateNProperty(@RequestBody @Valid Property property){
        Property result =  service.updateProperty(property);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteProperty(@RequestBody @Valid Property property) {
        Boolean result = service.deleteProperty(property);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/rooms/propBiggestRoom")
    public ResponseEntity<Room> propBiggestRoom(@RequestBody @Valid Property property) {
        Room propBiggestRoom = service.propBiggestRoom(property);
        return  ResponseEntity.ok().body(propBiggestRoom);
    }
}
