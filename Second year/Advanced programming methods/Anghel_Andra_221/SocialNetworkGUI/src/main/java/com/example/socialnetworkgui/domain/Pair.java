package com.example.socialnetworkgui.domain;

public class Pair <E1, E2> {
    private E1 e1;
    private E2 e2;
    public Pair(E1 e1, E2 e2) {
        this.e1 = e1;
        this.e2 = e2;
    }


    public E1 getE1() {
        return e1;
    }


    public void setE1(E1 e1) {
        this.e1 = e1;
    }


    public E2 getE2() {
        return e2;
    }


    public void setE2(E2 e2) {
        this.e2 = e2;
    }

    @Override
    public String toString() {
        return "" + e1 + "," + e2;
    }

}
