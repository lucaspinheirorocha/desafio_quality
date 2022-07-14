package com.github.transformeli.desafio_quality.util;

import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;

import java.util.HashSet;
import java.util.Set;

public class TestUtilsProperty {

    private static Set<Room> buildRooms()
    {
        Set<Room> roomsUtil = new HashSet<>();
        roomsUtil.add(new Room("Cozinha", 10.00, 22.00));
        roomsUtil.add(new Room("Banheiro", 5.00, 10.00));
        roomsUtil.add(new Room("Sala", 15.00, 23.00));
        return roomsUtil;
    }
    public static Property getNewProperty()
    {
        Set<Room> rooms = buildRooms();
        return buildProperty("Casa da Lari", "Penha", rooms);
    }

    public static Property buildProperty(
            String name,
            String neighborhood,
            Set<Room> rooms
    )
    {

        return Property.builder()
            .name(name)
            .neighborhood(TestUtilsNeighborhood.findByName(neighborhood))
            .rooms(rooms).build();
    }

    /**
     * @author Larissa Navarro
     * @author Lucas Pinheiro
     * @return
     */
    public static Set<Property> buildThreeProperties() {
        Set<Property> list = new HashSet<>();
        list.add(TestUtilsProperty.getNewProperty());
        list.add(TestUtilsProperty.buildProperty("Casa do Isaias", "Vila Matilde", buildRooms()));
        list.add(TestUtilsProperty.buildProperty("Casa do Lucas", "Jabotiana", buildRooms()));

        return list;
    }

}
