package com.example.java_mpp_bun.service;



import com.example.java_mpp_bun.domain.Bilet;
import com.example.java_mpp_bun.domain.User;
import com.example.java_mpp_bun.domain.Zbor;
import com.example.java_mpp_bun.repository.BiletRepository;
import com.example.java_mpp_bun.repository.UserRepository;
import com.example.java_mpp_bun.repository.ZborRepository;
import com.example.java_mpp_bun.utils.ObservableImplementat;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


public class Service extends ObservableImplementat {
    UserRepository repou;
    ZborRepository repoz;
    BiletRepository repob;

    Integer user_id;

    public void setService(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("C:\\Documente\\GitHub\\mpp-proiect-java-Andra1028\\Project1_MPP\\bd.config"));
        } catch (
                IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

        repou = new UserRepository(props);
        repoz = new ZborRepository(props);
        repob = new BiletRepository(props);
    }

    public List<Zbor> findAllFlignts()
    {
        return repoz.findAll();
    }

    public User login(String username, String password){
        User user= repou.findByName(username);
        if(user==null)
            throw new RuntimeException("User not found");
        user_id=user.getID();
        return user;
    }

    public List<Zbor> findFlight(String destination, LocalDate data){
        List<Zbor> zboruri = new ArrayList<>();
        for(Zbor zbor: repoz.findAll()){
            if(zbor.getLocuri() >0 && Objects.equals(zbor.getDestinatie(), destination)
                    && zbor.getData_ora().getYear()==data.getYear() && zbor.getData_ora().getMonth()==data.getMonth()
                    && zbor.getData_ora().getDayOfMonth()==data.getDayOfMonth())
                zboruri.add(zbor);
        }
        return zboruri;
    }

    public int generateId(){
        int maxi =0;
        for(Bilet ticket: repob.findAll())
        {
            if(ticket.getID()>maxi)
                maxi=ticket.getID();
        }
        maxi++;
        return maxi;
    }


    public void buyTicket(Integer zbor_id, String client_nume, String client_adresa, Integer nr_locuri, String turisti)
    {
        int id= generateId();
        List<String> t= List.of(turisti.split(","));
        Bilet bilet = new Bilet(zbor_id,user_id,client_nume,t,client_adresa,nr_locuri);
        bilet.setID(id);
        repob.save(bilet);
        Zbor zbor = repoz.findOne(zbor_id);
        zbor.setLocuri(zbor.getLocuri()-nr_locuri);
        repoz.update(zbor);
    }

    public void logout(){
        user_id= null;
    }

    public List<Zbor> availableFlights(){
        return null;
    }

}
