package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.exception.PreconditionFailedException;
import org.springframework.util.LinkedCaseInsensitiveMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PropertyRepository implements ICrud<Property> {

    private Map<String,Property> memoryDB = new HashMap<>();

    public String getKey(Property property){
        return property.getName().toLowerCase().replace(" ", "");
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
     * @param key property name
     */
    @Override
    public Property findByKey(String key) {
        return this.memoryDB.get(key.toLowerCase());
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
        }
        throw new NotFoundException("The property not exist.");
    }


}
