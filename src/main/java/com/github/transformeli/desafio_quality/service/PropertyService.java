package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.*;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.exception.ErrorPropertyRequestException;
import com.github.transformeli.desafio_quality.repository.NeighborhoodRepository;
import com.github.transformeli.desafio_quality.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PropertyService implements IPropertyService {

    @Autowired
    PropertyRepository repository;
    @Autowired
    NeighborhoodRepository neighborhoodRepository;

    public boolean validation(Property property) {
        if (property.getRooms().isEmpty() || property.getNeighborhood() == null) {
            throw new NotFoundException("Same attributes not found ");
        }
        return true;

    }

    public boolean validation(Room room) {
        if (room == null) {
            throw new NotFoundException("Room not found.");
        }
        return true;
    }

    /**
     * Total area calculator per room
     *
     * @param room
     * @author Isaias Finger and Rebecca Cruz
     */
    public Double roomTotalArea(Room room) {
        validation(room);
        return room.getLength() * room.getWidth();
    }

    /**
     * Total area calculator per property
     *
     * @param property
     * @author Isaias Finger and Rebecca Cruz
     */
    public Double propTotalArea(Property property) {
        validation(property);
        AtomicReference<Double> total = new AtomicReference<>(0D);
        property.getRooms().stream()
                .forEach(r -> total.updateAndGet(v -> v + roomTotalArea(r)));
        return total.get();
    }

    /**
     * Return biggest property's room
     *
     * @param property
     * @author Rebecca Cruz and Isaias Finger
     */
    public Room propBiggestRoom(Property property) {
        validation(property);
        return property.getRooms().stream().max(Comparator.comparing(r -> roomTotalArea(r))).get();
    }

    /**
     * Return property price per neighborhood
     *
     * @param property
     * @author Rebecca Cruz and Isaias Finger
     */
    public Double propPriceByNeighborhood(Property property) {
        validation(property);
        Double propArea = propTotalArea(property);
        Double sqPrice = property.getNeighborhood().getSqMeterPrice();
        return propArea * sqPrice;
    }

    /**
     * Calculate room area.
     *
     * @param room Room to calculate
     * @return RoomDTO
     * @author Isaias Finger
     */
    public RoomDTO roomAreaCalculator(Room room) {
        return RoomDTO.builder()
                .name(room.getName())
                .length(room.getLength())
                .width(room.getWidth())
                .roomArea(roomTotalArea(room))
                .build();
    }

    /**
     * Calculate total area and price of property,
     * the biggest room value, area value by room.
     *
     * @param prop Property to calculate
     * @return PropertyDTO
     * @author Isaias Finger & Lucas Pinheiro Rocha & Evelyn Critini Oliveira
     */
    public PropertyDTO propAreaCalculator(Property prop) {
        PropertyDTO dto = new PropertyDTO();
        dto.setName(prop.getName());
        Set<RoomDTO> rooms = new HashSet<>();
        prop.getRooms().forEach(r -> rooms.add(roomAreaCalculator(r)));
        dto.setRooms(rooms);
        dto.setNeighborhood(prop.getNeighborhood());
        dto.setBiggestRoom(roomAreaCalculator(propBiggestRoom(prop)));
        dto.setPropertyArea(propTotalArea(prop));
        dto.setPropertyPrice(propPriceByNeighborhood(prop));
        return dto;
    }

    /**
     * Find all Property
     *
     * @author Isaias Finger
     */
    public Set<Property> findAll() {
        return repository.findAll();
    }

    /**
     * Find Property by name
     *
     * @param name Name of Property
     * @author Isaias Finger
     */
    public Optional<Property> findByKey(String name) {
        return repository.findByKey(name);
    }

    private Property validPropertyNeighborhood(Property property) {
        Optional<Neighborhood> neighborhood
                = neighborhoodRepository.findByKey(property.getNeighborhood().getName());
        if (neighborhood.isPresent()) {
            if(property.getNeighborhood().getSqMeterPrice() != null) {
                neighborhoodRepository.update(neighborhood.get().getName(), property.getNeighborhood());
            }
            property.setNeighborhood(neighborhood.get());
            return repository.update(property.getName(), property);
        } else {
            if(property.getNeighborhood().getSqMeterPrice() == null) {
                repository.delete(property.getName());
                throw new NotFoundException("neighborhood not found");
            }
            neighborhoodRepository.create(property.getNeighborhood());
        }
        return property;
    }

    /**
     * Create new Property
     *
     * @param property
     * @return Property
     * @author Evelyn Cristini Oliveira / Alexandre Borges Souza
     */
    public Property createNewProperty(Property property) {
        Property result = repository.create(property);
        if (result != null) {
            return validPropertyNeighborhood(result);
        }
        throw new ErrorPropertyRequestException("Não foi possível criar essa propriedade.");
    }

    /**
     * Update an Property
     *
     * @param property
     * @return Property
     * @author Alexandre Borges Souza / Evelyn Cristini Oliveira
     */
    @Override
    public Property updateProperty(String name, Property property) {
        Property result = repository.update(name, property);
        if (result != null) {
            return validPropertyNeighborhood(result);
        }
        throw new ErrorPropertyRequestException("Não foi possível atualizar a propriedade.");
    }

    /**
     * Delete a Property
     *
     * @param name Name of Property
     * @return Property
     * @author Evelyn Cristini Oliveira / Alexandre Borges Souza
     */
    @Override
    public Boolean deleteProperty(String name) {
        return repository.delete(name);
    }
}
