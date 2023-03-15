package com.example.examen.repository;

public interface Repo<T> {
    Iterable<T> findAll();

}
