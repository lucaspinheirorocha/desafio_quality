package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PropertyService implements IPropertyService {

    /**
     * Total area calculator per room
     *
     * @param room
     * @author Isaias Finger and Rebecca Cruz
     */
    public Double roomTotalArea(Room room) {
        return room.getLength() * room.getWidth();
    }

    public Double roomTotalArea(Property property)
    {
        return roomTotalArea(property.getRooms().stream().findFirst().get());
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

}
