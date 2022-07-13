package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class NeighborhoodRepository implements ICrud<Neighborhood> {

    private Map<String, Neighborhood> memoryDB = new HashMap<>();

    private String getKey(Neighborhood neighborhood)
    {
        return neighborhood.getName().toLowerCase();
    }

    /**
     * Find neighborhood by Id
     * @author Transformeli
     * @param key Neighborhood Name
     */
    public Neighborhood findByKey(String key)
    {
        return this.memoryDB.get(key.toLowerCase());
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
        // TODO: customException
        throw new NotFoundException("neighborhood already exists");
    }

    /**
     * Update a neighborhood
     * @param neighborhood Class Instance
     * @author Transformeli
     */
    public Neighborhood update(Neighborhood neighborhood) {
        String key = getKey(neighborhood);
        if (memoryDB.containsKey(key)) {
            memoryDB.put(key, neighborhood);
            return memoryDB.get(key);
        }
        throw new NotFoundException("neighborhood not found");
    }

    /**
     * Delete a neighborhood
     * @param neighborhood Class Instance
     * @author Transformeli
     */
    public Boolean delete(Neighborhood neighborhood) {
        String key = getKey(neighborhood);
        if (memoryDB.containsKey(key)) {
            memoryDB.remove(key);
            return true;
        }
        return false;
    }

}
