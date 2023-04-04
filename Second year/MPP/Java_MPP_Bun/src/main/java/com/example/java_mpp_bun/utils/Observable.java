package com.example.java_mpp_bun.utils;

public interface Observable {
    public void addObserver(Observer e);
    public void removeObserver(Observer e);
    public void notifyObservers();
}
