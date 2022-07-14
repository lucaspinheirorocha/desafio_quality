package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.exception.PreconditionFailedException;
import java.util.*;

public class PropertyRepository implements ICrud<Property> {

    private Map<String,Property> memoryDB = new HashMap<>();

    private String getKey(String name) {
        return name.toLowerCase().replace(" ", "");
    }

    public String getKey(Property property) {
        return getKey(property.getName());
    }

    /**
     * find all property into database
     * @author laridevmeli
     */
    @Override
    public Set<Property> findAll() {
        Set<Property> loadedData = new HashSet<>();
        loadedData.addAll(memoryDB.values());
        return loadedData;
    }
    /**
     * find a element by key
     * @author laridevmeli
     * @param name property name
     */
    @Override
    public Optional<Property> findByKey(String name) {
        String key = getKey(name);
        if(this.memoryDB.containsKey(key)) {
            return Optional.of(this.memoryDB.get(key));
        }
        return Optional.empty();
    }

    /**
     * create property element
     * @author laridevmeli
     * @param property element object
     */
    @Override
    public Property create(Property property) {
        String key = getKey(property);
        if(!memoryDB.containsKey(key)){
            memoryDB.put(key,property);
            return property;
        }
        throw new PreconditionFailedException("The property already exists.");
    }

    /**
     * update property element
     * @author laridevmeli
     * @param property element object
     */

    @Override
    public Property update(Property property) {
        String key = getKey(property);
        if(memoryDB.containsKey(key)){
            memoryDB.put(key,property);
            return memoryDB.get(key);
        }
        throw new NotFoundException("The property not exists.");
    }

    /**
     * delete property element
     * @author laridevmeli
     * @param property element object
     */

    @Override
    public Boolean delete(Property property) {
        String key = getKey(property);
        if(memoryDB.containsKey(key)){
            memoryDB.remove(key,property);
            return true;
        }
        throw new NotFoundException("The property not exist.");
    }


}
