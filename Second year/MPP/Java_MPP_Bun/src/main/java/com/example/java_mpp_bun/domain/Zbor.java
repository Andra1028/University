package com.example.java_mpp_bun.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Zbor extends Entity<Integer>{
    private String destinatie;
    private LocalDateTime data_ora;
    private String aeroport;
    private Integer locuri;

    public Zbor(String destinatie, LocalDateTime data_ora, String aeroport, Integer locuri) {
        this.destinatie = destinatie;
        this.data_ora = data_ora;
        this.aeroport = aeroport;
        this.locuri = locuri;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public LocalDateTime getData_ora() {
        return data_ora;
    }

    public void setData_ora(LocalDateTime data_ora) {
        this.data_ora = data_ora;
    }

    public String getAeroport() {
        return aeroport;
    }

    public void setAeroport(String aeroport) {
        this.aeroport = aeroport;
    }

    public Integer getLocuri() {
        return locuri;
    }

    public void setLocuri(Integer locuri) {
        this.locuri = locuri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zbor zbor = (Zbor) o;
        return Objects.equals(destinatie, zbor.destinatie) && Objects.equals(data_ora, zbor.data_ora) && Objects.equals(aeroport, zbor.aeroport) && Objects.equals(locuri, zbor.locuri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinatie, data_ora, aeroport, locuri);
    }

    @Override
    public String toString() {
        return "Zbor{" +
                "destinatie='" + destinatie + '\'' +
                ", data_ora=" + data_ora +
                ", aeroport='" + aeroport + '\'' +
                ", locuri=" + locuri +
                '}';
    }
}
