package com.example.zboruri.utils;

import com.example.zboruri.domain.Flight;

public class FlightChangeEvent implements Events{
    private ChangeEventType type;
    private Flight oldData, data;

    public FlightChangeEvent(ChangeEventType type, Flight data) {
        this.type = type;
        this.data = data;
    }

    public FlightChangeEvent(ChangeEventType type, Flight oldData, Flight data) {
        this.type = type;
        this.oldData = oldData;
        this.data = data;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Flight getOldData() {
        return oldData;
    }

    public Flight getData() {
        return data;
    }
}
