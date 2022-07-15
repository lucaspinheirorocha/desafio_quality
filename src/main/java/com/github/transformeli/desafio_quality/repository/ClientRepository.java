package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Client;
import com.github.transformeli.desafio_quality.dto.Neighborhood;
import com.github.transformeli.desafio_quality.exception.BadRequestException;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ClientRepository implements ICrud<Client> {

    private Map<String,Client> memoryDB = null;

    public ClientRepository () {
        memoryDB = new HashMap<>();
    }

    private String getKey(String name)
    {
        return name.toLowerCase().replace(" ", "");
    }

    private String getKey(Client client)
    {
        return getKey(client.getName());
    }

    /**
     * Find all clients from database
     * @author Alexandre Borges Souza e Evelyn Cristini Oliveira
     */
    @Override
    public Set<Client> findAll() {
        Set<Client> loadedData = new HashSet<>();
        loadedData.addAll(memoryDB.values());
        return loadedData;
    }

    /**
     * Find client by key
     * @author Evelyn Cristini Oliveira e Alexandre Borges Souza
     * @param key Client Name
     */
    @Override
    public Optional<Client> findByKey(String key) {
        key = key.toLowerCase();
        if(this.memoryDB.containsKey(key)) {
            return Optional.of(this.memoryDB.get(key));
        }
        return Optional.empty();
    }

    /**
     * Create a client
     * @param client Class Instance
     * @author Evelyn Cristini Oliveira e Alexandre Borges Souza
     */
    @Override
    public Client create(Client client) {
        String key = getKey(client);
        if (!memoryDB.containsKey(key)) {
            memoryDB.put(key, client);
            return memoryDB.get(key);
        }
        throw new BadRequestException("Client already exists");
    }

    /**
     * Update a client
     * @param name Name of Client
     * @param client Class Instance
     * @author Alexandre Borges Souza e Evelyn Cristini Oliveira
     */
    @Override
    public Client update(String name, Client client) {
        String oldKey = getKey(client);
        if (memoryDB.containsKey(oldKey)) {
            memoryDB.remove(oldKey);
            String newKey = getKey(client.getName());
            memoryDB.put(newKey, client);
            return memoryDB.get(newKey);
        }
        throw new NotFoundException("Client not found");
    }

    /**
     * Delete a client
     * @param name Name of Client
     * @author Alexandre Borges Souza e Evelyn Cristini Oliveira
     */

    @Override
    public Boolean delete(String name) {
        String key = getKey(name);
        if (memoryDB.containsKey(key)) {
            memoryDB.remove(key);
            return true;
        }
        return false;
    }
}
