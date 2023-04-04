package com.example.examen.controller;

import com.example.examen.HelloApplication;
import com.example.examen.domain.City;
import com.example.examen.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartController {

    Service srv;
    Integer nr=0;
    String combo1;
    String combo2;

    public void setService(Service srv, String combo1, String combo2)
    {
        this.srv=srv;
        this.combo1=combo1;
        this.combo2=combo2;
    }

    @FXML
    protected void handleStart(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("CityPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Cities");
            stage.setScene(scene);
            CityController cityController = fxmlLoader.getController();
            nr++;
           cityController.setService(srv, nr, combo1, combo2);
            stage.show();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
