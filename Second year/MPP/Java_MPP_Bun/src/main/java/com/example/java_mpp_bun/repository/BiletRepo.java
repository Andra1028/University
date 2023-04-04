package com.example.java_mpp_bun.repository;


import com.example.java_mpp_bun.domain.Bilet;

public interface BiletRepo extends Repository<Integer, Bilet>{
    void save (Bilet entity);
}
