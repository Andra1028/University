package com.example.java_mpp_bun.domain;

import java.util.List;
import java.util.Objects;

public class Bilet extends Entity<Integer>{
    private Integer zbor_id;
    private Integer angajat_id;
    private String client_nume;
    private List<String> turisti;
    private String client_adresa;
    private Integer nr_locuri;

    public Bilet(Integer zbor_id,Integer angajat_id, String client_nume, List<String> turisti, String client_adresa, Integer nr_locuri) {
        this.zbor_id = zbor_id;
        this.angajat_id=angajat_id;
        this.client_nume = client_nume;
        this.turisti = turisti;
        this.client_adresa = client_adresa;
        this.nr_locuri = nr_locuri;
    }

    public Integer getZbor_id() {
        return zbor_id;
    }

    public void setZbor_id(Integer zbor_id) {
        this.zbor_id = zbor_id;
    }

    public String getClient_nume() {
        return client_nume;
    }

    public void setClient_nume(String client_nume) {
        this.client_nume = client_nume;
    }

    public List<String> getTuristi() {
        return turisti;
    }

    public void setTuristi(List<String> turisti) {
        this.turisti = turisti;
    }

    public String getClient_adresa() {
        return client_adresa;
    }

    public void setClient_adresa(String client_adresa) {
        this.client_adresa = client_adresa;
    }

    public Integer getNr_locuri() {
        return nr_locuri;
    }

    public void setNr_locuri(Integer nr_locuri) {
        this.nr_locuri = nr_locuri;
    }

    public Integer getAngajat_id() {
        return angajat_id;
    }

    public void setAngajat_id(Integer angajat_id) {
        this.angajat_id = angajat_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bilet bilet = (Bilet) o;
        return zbor_id.equals(bilet.zbor_id) && client_nume.equals(bilet.client_nume) && Objects.equals(turisti, bilet.turisti) && client_adresa.equals(bilet.client_adresa) && nr_locuri.equals(bilet.nr_locuri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zbor_id, client_nume, turisti, client_adresa, nr_locuri);
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "zbor_id=" + zbor_id +
                ", client_nume='" + client_nume + '\'' +
                ", turisti=" + turisti +
                ", client_adresa='" + client_adresa + '\'' +
                ", nr_locuri=" + nr_locuri +
                '}';
    }
}
