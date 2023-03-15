package com.example.examen.domain;

import java.util.Objects;

public class TrainStation {
    private Integer trainId;
    private int departureCityId;
    private int destinationCityId;

    public TrainStation(Integer trainId, int departureCityId, int destinationCityId) {
        this.trainId = trainId;
        this.departureCityId = departureCityId;
        this.destinationCityId = destinationCityId;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public int getDepartureCityId() {
        return departureCityId;
    }

    public void setDepartureCityId(int departureCityId) {
        this.departureCityId = departureCityId;
    }

    public int getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(int destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainStation that = (TrainStation) o;
        return departureCityId == that.departureCityId && destinationCityId == that.destinationCityId && Objects.equals(trainId, that.trainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainId, departureCityId, destinationCityId);
    }

    @Override
    public String toString() {
        return "TrainStation{" +
                "trainId=" + trainId +
                ", departureCityId=" + departureCityId +
                ", destinationCityId=" + destinationCityId +
                '}';
    }
}
