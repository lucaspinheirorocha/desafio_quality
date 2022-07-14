package com.github.transformeli.desafio_quality.controller;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.exception.BadRequestException;
import com.github.transformeli.desafio_quality.service.IPropertyService;
import com.github.transformeli.desafio_quality.service.PropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private IPropertyService service = new PropertyService();

    @GetMapping("/")
    public ResponseEntity<Void> index()
    {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rooms/propBiggestRoom")
    public ResponseEntity<Room> propBiggestRoom(@RequestBody @Valid Property property) {
        Room propBiggestRoom = service.propBiggestRoom(property);
        return  ResponseEntity.ok().body(propBiggestRoom);
    }
}
