package com.github.transformeli.desafio_quality.controller;

import com.github.transformeli.desafio_quality.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    @GetMapping("/")
    public ResponseEntity<Void> index()
    {
        return ResponseEntity.ok().build();
    }
}
