package com.github.transformeli.desafio_quality.util;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestUtilsProperty2 {

    public static Room getNewRoom() {
        Room room = new Room();
        room.setName("Test");
        room.setLength(2D);
        room.setWidth(3D);
        return room;
    }

    public static Property getNewProperty()
    {
        Property prop = new Property();
        prop.setName("Prop Test");
        prop.setNeighborhood(TestUtilsNeighborhood.getNewNeighborhood());
        Set<Room> roomsTest = new HashSet<>();

        roomsTest.add(getNewRoom());

        Room room2 = new Room();
        room2.setName("Test 2");
        room2.setLength(4D);
        room2.setWidth(6D);
        roomsTest.add(room2);

        prop.setRooms(roomsTest);
        return prop;
    }

    public static Room propBiggestRoom() {
        Property property = getNewProperty();
        return property.getRooms().stream().max(Comparator.comparing(r -> r.getLength() * r.getWidth())).get();
    }

    public static Room propBiggestNoRoom () {
        return  Room.builder().name("").width(null).length(null).build();
    }
}
