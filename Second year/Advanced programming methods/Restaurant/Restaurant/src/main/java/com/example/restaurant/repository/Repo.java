package com.example.restaurant.repository;

import java.util.List;

public interface Repo <T>{
    Iterable<T> findAll();
    T save (T entity);

}
