package com.example.java_mpp_bun;

import com.example.java_mpp_bun.domain.Zbor;
import com.example.java_mpp_bun.service.Service;
import com.example.java_mpp_bun.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FindFlightsController implements Observer {
    @FXML
    TableView<Zbor> flightsTable;
    @FXML
    TableColumn<Zbor, String> destinationColumn;

    @FXML
    TableColumn<Zbor, LocalDateTime> dateColumn;
    @FXML
    TableColumn<Zbor, String> aeroportColumn;
    @FXML
    TableColumn<Zbor, Integer> sitsColumn;

    @FXML
    private TextField nameField;
    @FXML
    private TextField touristField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField sitsField;
    @FXML
    private Label messageLabel;

    Service srv;

    String destination;
    LocalDate date;

    ObservableList<Zbor> model = FXCollections.observableArrayList();

    public void setService (Service srv, String destination, LocalDate date){
        this.srv=srv;
        this.destination=destination;
        this.date=date;
        srv.addObserver(this);
        initModel();
    }

    @FXML
    public void initialize(){
        destinationColumn.setCellValueFactory(new PropertyValueFactory<Zbor, String>("destinatie"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Zbor, LocalDateTime>("data_ora"));
        aeroportColumn.setCellValueFactory(new PropertyValueFactory<Zbor, String>("aeroport"));
        sitsColumn.setCellValueFactory(new PropertyValueFactory<Zbor, Integer>("locuri"));
        flightsTable.setItems(model);
    }


    private void initModel() {
        Iterable<Zbor> zboruri = srv.findFlight(destination, date);
        System.out.println(zboruri);
        List<Zbor> zbor = StreamSupport.stream(zboruri.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(zbor);
    }

    @Override
    public void update (){initModel();}

    @FXML
    protected void buy(ActionEvent actionEvent){
        try{
            Integer zbor_id = flightsTable.getSelectionModel().getSelectedItem().getID();
            String client_nume= nameField.getText();
            String turisti = touristField.getText();
            String adresa = addressField.getText();
            Integer locuri = Integer.valueOf(sitsField.getText());
            srv.buyTicket(zbor_id,client_nume,adresa,locuri,turisti);
            nameField.clear();
            touristField.clear();
            addressField.clear();
            sitsField.clear();
            messageLabel.setText("Inregistrare cu succes");
        }catch(Exception e)
        {
            messageLabel.setText(e.getMessage());
            nameField.clear();
            touristField.clear();
            addressField.clear();
            sitsField.clear();
        }
    }
}
