package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import com.github.transformeli.desafio_quality.exception.ErrorPropertyRequestException;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PropertyService implements IPropertyService {

    @Autowired
    private PropertyRepository repository;

    /**
     * Total area calculator per room
     *
     * @param room
     * @author Isaias Finger and Rebecca Cruz
     */
    public Double roomTotalArea(Room room) {
        return room.getLength() * room.getWidth();
    }

    /**
     * Total area calculator per property
     *
     * @param property
     * @author Isaias Finger and Rebecca Cruz
     */
    public Double propTotalArea(Property property) {
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
        return property.getRooms().stream().max(Comparator.comparing(r -> roomTotalArea(r))).get();
    }

    /**
     * Return property price per neighborhood
     *
     * @param property
     * @author Rebecca Cruz and Isaias Finger
     */
    public Double propPriceByNeighborhood(Property property) {
        Double propArea = propTotalArea(property);
        Double sqPrice = property.getNeighborhood().getSqMeterPrice();
        return propArea * sqPrice;
    }

    /**
     * Create new Property
     *
     * @param property
     * @author Evelyn Cristini Oliveira / Alexandre Borges Souza
     * @return Property
     */
    public Property createNewProperty(Property property) {
        Property result = repository.create(property);
        if (result != null) {
            return result;
        }
        throw new ErrorPropertyRequestException("Não foi possível criar nova pessoa proprietária.");
    }

    /**
     * Update an Property
     *
     * @param property
     * @author  Alexandre Borges Souza / Evelyn Cristini Oliveira
     * @return Property
     */
    @Override
    public Property updateProperty(Property property) {

       Property result = repository.update(property);
        if (result != null) {
            return result;
        }
        throw new ErrorPropertyRequestException("Não foi possível criar nova pessoa proprietária.");
    }

    /**
     * Delete an Property
     *
     * @param property
     * @author  Evelyn Cristini Oliveira / Alexandre Borges Souza
     * @return Property
     */
    @Override
    public Boolean deleteProperty(Property property) {
        return repository.delete(property);
    }
}
