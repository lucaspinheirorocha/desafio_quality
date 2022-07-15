package com.github.transformeli.desafio_quality.util;

import com.github.transformeli.desafio_quality.dto.Neighborhood;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TestUtilsNeighborhood {

    public static Neighborhood getNewNeighborhood()
    {
        return buildNeighborhood("Bela Vista", 30.0D);
    }

    public static Neighborhood buildNeighborhood(String name, Double sqMeterPrice)
    {
        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setName(name);
        neighborhood.setSqMeterPrice(sqMeterPrice);
        return neighborhood;
    }

    public static Set<Neighborhood> getSetOfNeighborhood()
    {
        Set<Neighborhood> list = new HashSet<>();
        list.add(getNewNeighborhood());
        list.add(buildNeighborhood("Penha", 15.0D));
        list.add(buildNeighborhood("Jabotiana", 10.0D));
        list.add(buildNeighborhood("Vila Matilde", 5.0D));
        return list;
    }

    public static Neighborhood findByName(String neighborhoodName)
    {
        Set<Neighborhood> allNeighborhoods = TestUtilsNeighborhood.getSetOfNeighborhood();
        String nameTrimmed = neighborhoodName.replace(" ", "");
        List<Neighborhood> foundNeighborhood = allNeighborhoods
                .stream()
                .filter(n -> n.getName().replace(" ", "")
                        .equalsIgnoreCase(nameTrimmed)).collect(Collectors.toList());
        if(foundNeighborhood.isEmpty())
        {
            return buildNeighborhood(neighborhoodName, 1.0D);
        }
        return foundNeighborhood.get(0);
    }
}
