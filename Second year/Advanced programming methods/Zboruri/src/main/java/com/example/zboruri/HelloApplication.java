package com.example.zboruri;

import com.example.zboruri.controller.Flights;
import com.example.zboruri.controller.Login;
import com.example.zboruri.domain.Client;
import com.example.zboruri.domain.Flight;
import com.example.zboruri.domain.Ticket;
import com.example.zboruri.repository.ClientRepo;
import com.example.zboruri.repository.FlightRepo;
import com.example.zboruri.repository.Repo;
import com.example.zboruri.repository.TicketRepo;
import com.example.zboruri.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    Repo<Flight> repof= new FlightRepo("jdbc:postgresql://localhost:5432/zboruri", "postgres", "1234");
    Repo<Client> repoc= new ClientRepo("jdbc:postgresql://localhost:5432/zboruri", "postgres", "1234");
    Repo<Ticket> repot= new TicketRepo("jdbc:postgresql://localhost:5432/zboruri", "postgres", "1234");


    Service srv = new Service(repoc,repof,repot);
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene =new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        Login loginController = fxmlLoader.getController();
        loginController.setService(srv);
        stage.show();
        stage.show();
        //showFlights();

    }


    public static void main(String[] args) {
        launch();
    }
}