package com.github.transformeli.desafio_quality.util;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.dto.Room;
import java.util.*;

public class TestUtilsProperty {

    public static Set<Room> buildRooms()
    {
        Set<Room> roomsUtil = new HashSet<>();
        roomsUtil.add(new Room("Cozinha", 10.00, 22.00));
        roomsUtil.add(new Room("Banheiro", 5.00, 10.00));
        roomsUtil.add(new Room("Sala", 15.00, 23.00));
        return roomsUtil;
    }

    /**
     * Provides a property that will be util to units tests
     * @author Lucas Pinheiro
     * @return
     */
    public static Property getNewProperty()
    {
        Set<Room> rooms = buildRooms();
        return buildProperty("Casa da Lari", "Penha", rooms);
    }

    /**
     *
     * @param name property name
     * @param neighborhood neighborhood name
     * @param rooms list of rooms
     * @return
     */
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
     * This method provides three properties to be used in unit tests
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

    public static Property getEmptyRooms()
    {
        return buildProperty("Casa da Lari", "Penha",new HashSet<>());
    }

    /**
     * Get new room
     *
     * @author Isaias Finger and Rebecca Cruz
     * @return
     */
    public static Room getNewRoom() {
        Room room = new Room();
        room.setName("Test");
        room.setLength(2D);
        room.setWidth(3D);
        return room;
    }

    /**
     * Gerenate new Property Without Contets
     * @return Property
     * @author Alexandre Borges Souza
     */
    public static Property generateNewPropertyWhitoutContents () {
        return Property.builder().name("").build();
    }

    public static Double propPriceByNeighborhoodWithValue() {
        return 300.00;
    }

    public static Double propPriceByNeighborhoodWithoutValue() {
        return 0.0;
    }

    public static Room propBiggestRoom() {
        Property property = getNewProperty();
        return property.getRooms().stream().max(Comparator.comparing(r -> r.getLength() * r.getWidth())).get();
    }

    public static Room propBiggestNoRoom () {
        return  Room.builder().name("").width(null).length(null).build();
    }


}
