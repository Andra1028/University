package com.example.zboruri.controller;

import com.example.zboruri.HelloApplication;
import com.example.zboruri.domain.Client;
import com.example.zboruri.domain.Flight;
import com.example.zboruri.repository.ClientRepo;
import com.example.zboruri.repository.FlightRepo;
import com.example.zboruri.repository.Repo;
import com.example.zboruri.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
    @FXML
    private TextField usernameField;

    Service srv;

    public void setService(Service srv)
    {
        this.srv=srv;
    }

    @FXML
    protected void login(ActionEvent actionEvent) {
        try {

            String username = usernameField.getText();

            Client clientLogin = srv.connectClient(username);
            //System.out.println(srv.connectClient(username));
            if(clientLogin == null) {
                System.out.println("intra in null");
                throw new Exception();
            }
            usernameField.clear();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FilteredFlights.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(clientLogin.getName());
            stage.setScene(scene);
            Flights userController = fxmlLoader.getController();
            userController.setService(srv,clientLogin.getUsername());
            stage.show();
            //System.out.println("aici");
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            usernameField.clear();
            System.out.println("nu e bun");
        }
    }
}
