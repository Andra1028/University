package com.example.examen;

import com.example.examen.controller.StartController;
import com.example.examen.domain.City;
import com.example.examen.domain.TrainStation;
import com.example.examen.domain.validation.CityValidator;
import com.example.examen.domain.validation.TrainStationValidator;
import com.example.examen.repository.CityRepo;
import com.example.examen.repository.Repo;
import com.example.examen.repository.TrainStationRepo;
import com.example.examen.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    Repo<City> repoc = new CityRepo("jdbc:postgresql://localhost:5432/examen", "postgres", "1234", new CityValidator());
    Repo<TrainStation> repot = new TrainStationRepo("jdbc:postgresql://localhost:5432/examen", "postgres", "1234", new TrainStationValidator());

    Service srv = new Service(repoc, repot);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartPage.fxml"));
        Scene scene =new Scene(fxmlLoader.load());
        stage.setTitle("Start");
        stage.setScene(scene);
        StartController startController = fxmlLoader.getController();
        startController.setService(srv, "", "");
        stage.show();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}