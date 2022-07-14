package com.github.transformeli.desafio_quality.repository;

import com.github.transformeli.desafio_quality.dto.Client;
import com.github.transformeli.desafio_quality.exception.BadRequestException;
import com.github.transformeli.desafio_quality.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class ClientRepository implements ICrud<Client> {

    private Map<String,Client> memoryDB = null;

    public ClientRepository () {
        memoryDB = new HashMap<>();
    }

    private String getKey(Client client)
    {
        return client.getName().toLowerCase();
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
    public Client findByKey(String key) { return this.memoryDB.get(key.toLowerCase()); }

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
        // TODO: customException
        throw new BadRequestException("Client already exists");
    }

    /**
     * Update a client
     * @param client Class Instance
     * @author Alexandre Borges Souza e Evelyn Cristini Oliveira
     */

    @Override
    public Client update(Client client) {
        String key = getKey(client);
        if (memoryDB.containsKey(key)) {
            memoryDB.put(key, client);
            return memoryDB.get(key);
        }
        throw new NotFoundException("Client not found");
    }

    /**
     * Delete a client
     * @param client Class Instance
     * @author Alexandre Borges Souza e Evelyn Cristini Oliveira
     */

    @Override
    public Boolean delete(Client client) {
        String key = getKey(client);
        if (memoryDB.containsKey(key)) {
            memoryDB.remove(key);
            return true;
        }
        return false;
    }
}
