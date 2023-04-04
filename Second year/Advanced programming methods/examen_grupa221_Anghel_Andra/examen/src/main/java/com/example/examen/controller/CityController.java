package com.example.examen.controller;

import com.example.examen.HelloApplication;
import com.example.examen.domain.City;
import com.example.examen.domain.TrainStation;
import com.example.examen.service.Service;
import com.example.examen.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class CityController implements Observer {

    @FXML
    ComboBox<String> departureCombo;
    @FXML
    ComboBox<String> destinationCombo;
    @FXML
    CheckBox directRoutesBox;
    @FXML
    Label routeLabel;
    @FXML
    Label filterLabel;
    Service srv;
    String route;

    String filtered;
    Integer nr;

    String departureVlaue;
    String ddestinationValue;

    ObservableList<String> city = FXCollections.observableArrayList();
    ObservableList<String> departureObs = FXCollections.observableArrayList();
    ObservableList<String> destinationObs = FXCollections.observableArrayList();
    public void setService(Service srv, Integer nr, String departureVlaue, String ddestinationValue)
    {
        this.srv=srv;
        departureCombo.setItems(city);
        destinationCombo.setItems(city);
        for(City c : srv.getCities()){
            city.add(c.getCityName());
        }
        this.nr=nr;
        this.departureVlaue=departureVlaue;
        this.ddestinationValue=ddestinationValue;

        route="";
        srv.addObserver(this);
    }

    public void handleSearch(ActionEvent event)
    {
        if(departureCombo.getSelectionModel().getSelectedItem()==null || destinationCombo.getSelectionModel().getSelectedItem()==null)
            return;
        if(directRoutesBox.isSelected()) {
            String departure = departureCombo.getSelectionModel().getSelectedItem();
            String destination = destinationCombo.getSelectionModel().getSelectedItem();
            departureObs.setAll(departure);
            destinationObs.setAll(destination);
            ///FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StartPage.fxml"));
            //StartController start = fxmlLoader.getController();
            //start.setService(srv,departure,destination);
            srv.notifyObservers();
            City depCity = srv.findCityByName(departure);
            //System.out.println(depCity);
            City desCity = srv.findCityByName(destination);
            //System.out.println(desCity);
            Iterable<TrainStation> ts = srv.findDirectTrainByCities(depCity.getCityId(), desCity.getCityId());
            System.out.println(ts);
            Integer number = nr-1;
            if(number>0)
                filterLabel.setText(number.toString()+ " other user(s) are  looking at the same route");
            if(ts==null)
                routeLabel.setText("Nu exista tren direct");
            else
            {
                Float pretStatie= 10F;
                int nrStatii=0;
                route = departure + " ";
                nrStatii++;
                for (TrainStation t : ts) {
                    nrStatii++;
                    City city = srv.findCityById(t.getDestinationCityId());
                route = route +  t.getTrainId() + " " + city.getCityName() + " ";
            }
                Float pret= pretStatie*nrStatii;
                route= route+ " price: " + pret.toString();
                routeLabel.setText(route);
            }
        }
        else {
            routeLabel.setText("nu e direct");
        }

    }

    @Override
    public void update (){filterLabel.setText(filtered);}
}

