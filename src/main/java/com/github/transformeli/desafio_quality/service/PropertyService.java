package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class PropertyService {

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

}
