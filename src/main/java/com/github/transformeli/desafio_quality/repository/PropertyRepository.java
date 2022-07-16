package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Property;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.exception.PreconditionFailedException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@NoArgsConstructor
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
    public Property update(String name, Property property) {
        String oldKey = getKey(name);
        if(memoryDB.containsKey(oldKey)) {
            memoryDB.remove(oldKey);
            String newKey = getKey(property.getName());
            memoryDB.put(newKey, property);
            return memoryDB.get(newKey);
        }
        throw new NotFoundException("The property not exists.");
    }

    /**
     * Delete property element
     * @author laridevmeli
     * @param name Name of Property
     */
    @Override
    public Boolean delete(String name) {
        String key = getKey(name);
        if(memoryDB.containsKey(key)) {
            memoryDB.remove(key);
            return true;
        }
        throw new NotFoundException("The property not exists.");
    }
}
