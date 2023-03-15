package com.example.restaurant.utils;

public interface Observable {
    public void addObserver(Observer e);
    public void removeObserver(Observer e);
    public void notifyObservers();
}
