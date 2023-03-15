package com.example.zboruri.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Flight {
    private Long fightId;
    private String from;
    private String to;
    private LocalDateTime departureTime;
    private LocalDateTime landingTime;
    private int noSits;

    private Integer index=5;

    public Flight(Long fightId, String from, String to, LocalDateTime departureTime, LocalDateTime landingTime,int noSits) {
        this.fightId = fightId;
        this.from = from;
        this.to = to;
        this.departureTime = departureTime;
        this.landingTime = landingTime;
        this.noSits=noSits;
    }

    public Long getFightId() {
        return fightId;
    }

    public void setFightId(Long fightId) {
        this.fightId = fightId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }

    public void setLandingTime(LocalDateTime landingTime) {
        this.landingTime = landingTime;
    }

    public int getNoSits() {
        return noSits;
    }

    public void setNoSits(int noSits) {
        this.noSits = noSits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(fightId, flight.fightId) && Objects.equals(from, flight.from) && Objects.equals(to, flight.to) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(landingTime, flight.landingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fightId, from, to, departureTime, landingTime);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "fightId=" + fightId +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", departureTime=" + departureTime +
                ", landingTime=" + landingTime +
                ", noSits=" + noSits +
                '}';
    }
}
