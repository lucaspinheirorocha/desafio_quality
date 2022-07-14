package com.github.transformeli.desafio_quality.repository;

import java.util.*;

public interface ICrud<T> {
    Set<T> findAll();
    Optional<T> findByKey(String key);
    T create(T object);
    T update(T object);
    Boolean delete(T object);
}
