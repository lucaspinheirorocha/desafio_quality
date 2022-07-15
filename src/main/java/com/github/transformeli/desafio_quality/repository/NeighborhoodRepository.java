package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import com.github.transformeli.desafio_quality.exception.PreconditionFailedException;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class NeighborhoodRepository implements ICrud<Neighborhood> {

    private Map<String, Neighborhood> memoryDB = new HashMap<>();

    private String getKey(String name)
    {
        return name.toLowerCase().replace(" ", "");
    }

    private String getKey(Neighborhood neighborhood)
    {
        return getKey(neighborhood.getName());
    }

    /**
     * Find neighborhood by Id
     * @author Transformeli
     * @param name Neighborhood Name
     */
    public Optional<Neighborhood> findByKey(String name)
    {
        String key = getKey(name);
        if(this.memoryDB.containsKey(key)) {
            return Optional.of(this.memoryDB.get(key));
        }
        return Optional.empty();
    }

    /**
     * Find all neighborhood from database
     * @author Transformeli
     */
    public Set<Neighborhood> findAll() {
        Set<Neighborhood> loadedData = new HashSet<>();
        loadedData.addAll(memoryDB.values());
        return loadedData;
    }

    /**
     * Create a neighborhood
     * @param neighborhood Class Instance
     * @author Transformeli
     */
    public Neighborhood create(Neighborhood neighborhood) {
        String key = getKey(neighborhood);
        if (!memoryDB.containsKey(key)) {
            memoryDB.put(key, neighborhood);
            return memoryDB.get(key);
        }
        throw new PreconditionFailedException("neighborhood already exists");
    }

    /**
     * Update a neighborhood
     * @param neighborhood Class Instance
     * @author Transformeli
     */
    public Neighborhood update(String name, Neighborhood neighborhood) {
        String oldKey = getKey(name);
        if (memoryDB.containsKey(oldKey)) {
            memoryDB.remove(oldKey);
            String newkey = getKey(neighborhood.getName());
            memoryDB.put(newkey, neighborhood);
            return memoryDB.get(newkey);
        }
        throw new NotFoundException("neighborhood not found");
    }

    /**
     * Delete a neighborhood
     * @param neighborhood Class Instance
     * @author Transformeli
     */
    public Boolean delete(String name) {
        String key = getKey(name);
        if (memoryDB.containsKey(key)) {
            memoryDB.remove(key);
            return true;
        }
        throw new NotFoundException("neighborhood not found");
    }

}
