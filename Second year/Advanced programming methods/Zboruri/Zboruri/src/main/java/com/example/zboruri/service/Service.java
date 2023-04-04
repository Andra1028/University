package com.example.zboruri.service;

import com.example.zboruri.domain.Client;
import com.example.zboruri.domain.Flight;
import com.example.zboruri.domain.Ticket;
import com.example.zboruri.repository.Repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Service {
    private Repo<Client> crepo;
    private Repo<Flight> frepo;
    private Repo<Ticket> trepo;
    private Integer index=5;

    public Service(Repo<Client> crepo,Repo<Flight> frepo,Repo<Ticket> trepo) {
        this.crepo = crepo;
        this.frepo=frepo;
        this.trepo=trepo;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Client connectClient(String username)
    {
        for(Client c: crepo.findAll())
            if(Objects.equals(c.getUsername(), username))
                return c;
        return null;
    }

    public Iterable<Flight> getFlightsFiltered(LocalDate departure, String from, String to)
    {
        Set<Flight> flights = new HashSet<>();
        Integer localIndex=0;
        for(Flight f: frepo.findAll()) {
            LocalDate day= f.getDepartureTime().toLocalDate();
            if (Objects.equals(f.getFrom(), from) && Objects.equals(f.getTo(), to) && day.isEqual(departure)) {
                localIndex++;
                if (localIndex <= index && localIndex > index - 5)
                    flights.add(f);
            }
        }
        return flights;
    }

    public Iterable<Flight> getFlights()
    {
        return frepo.findAll();
    }

    public Flight findFlight(Long id){
        for(Flight f: getFlights())
            if(Objects.equals(f.getFightId(), id))
                return f;
        return null;
    }

    public void buyTicket(String username, Long flight, LocalDateTime purchase){
        Ticket t= new Ticket(username,flight,purchase);
        Flight f =findFlight(flight);
        f.setNoSits(f.getNoSits()-1);
        trepo.save(t);
        frepo.update(f);
    }

}
