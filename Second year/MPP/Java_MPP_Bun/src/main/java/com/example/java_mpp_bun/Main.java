package com.example.java_mpp_bun;

import com.example.java_mpp_bun.domain.Bilet;
import com.example.java_mpp_bun.service.Service;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Service srv = new Service();
        srv.setService();
        List<String> turisti = new ArrayList<>();
        turisti.add("Maria");
        turisti.add("Costel");
        Bilet bilet = new Bilet(1,1,"Andra",turisti,"adresa",3);
        bilet.setID(3);
        System.out.println(srv.login("Andra", "1"));

        //srv.buyTicket(1,"Mirel", "cbd", 3, turisti);
    }
}