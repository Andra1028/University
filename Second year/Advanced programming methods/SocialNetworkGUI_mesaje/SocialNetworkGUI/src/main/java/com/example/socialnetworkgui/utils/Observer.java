package com.example.socialnetworkgui.utils;

public interface Observer<E extends Event> {
    void update(E e);
}
