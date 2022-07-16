package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.PropertyDTO;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.dto.RoomDTO;

import java.util.Optional;
import java.util.Set;

public interface IPropertyService {
    Double roomTotalArea(Room room);
    Double propTotalArea(Property property);
    Room propBiggestRoom(Property property);
    Double propPriceByNeighborhood(Property property);
    RoomDTO roomAreaCalculator(Room room);
    PropertyDTO propAreaCalculator(Property prop);
    Set<Property> findAll();
    Optional<Property> findByKey(String name);
    Property createNewProperty(Property property);
    Property updateProperty(String name, Property property);
    Boolean deleteProperty(String name);
}
