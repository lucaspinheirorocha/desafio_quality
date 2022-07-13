package com.github.transformeli.desafio_quality.repository;

import java.util.Set;

public interface ICrud<T> {
    Set<T> findAll();
    T findByKey(String key);
    T create(T object);
    T update(T object);
    Boolean delete(T object);
}
