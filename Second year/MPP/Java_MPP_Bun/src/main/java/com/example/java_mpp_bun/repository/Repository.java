package com.example.java_mpp_bun.repository;



import com.example.java_mpp_bun.domain.Entity;

import java.time.LocalDateTime;
import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {

    E findOne(ID id);
    List<E> findByDestination(String destinatie, LocalDateTime data);
    E findByName(String userName);
    Iterable<E> findAll();
    void save(E entity);
    void update( E entity);

}
