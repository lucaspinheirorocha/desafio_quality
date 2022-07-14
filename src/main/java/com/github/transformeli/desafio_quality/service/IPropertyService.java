package com.github.transformeli.desafio_quality.service;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;

public interface IPropertyService {
    Double roomTotalArea(Room room);
    Double propTotalArea(Property property);
    Room propBiggestRoom(Property property);
}
