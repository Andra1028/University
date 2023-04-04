package com.example.zboruri.utils;

public interface Observer<E extends Events> {
    void update(E e);
}
