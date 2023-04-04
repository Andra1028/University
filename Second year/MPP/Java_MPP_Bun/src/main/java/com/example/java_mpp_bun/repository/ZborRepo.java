package com.example.java_mpp_bun.repository;

import com.example.java_mpp_bun.domain.Zbor;

import java.time.LocalDateTime;
import java.util.List;

public interface ZborRepo extends Repository<Integer, Zbor>{
    List<Zbor> findByDestination(String destination, LocalDateTime data);
    void save(Zbor entity);
    void update(Zbor entity);
    List<Zbor> findAll();
}
