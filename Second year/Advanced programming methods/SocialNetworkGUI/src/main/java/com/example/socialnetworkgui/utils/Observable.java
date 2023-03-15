package com.example.socialnetworkgui.utils;

public interface Observable<E extends Event> {
    public void addObserver(Observer<E> e);
    public void removeObserver(Observer<E> e);
    public void notifyObservers(E t);
}
