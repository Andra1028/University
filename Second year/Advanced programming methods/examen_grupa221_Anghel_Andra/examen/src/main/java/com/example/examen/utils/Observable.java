package com.example.examen.utils;

public interface Observable {
    public void addObserver(Observer e);
    public void removeObserver(Observer e);
    public void notifyObservers();
}
