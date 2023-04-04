package com.example.zboruri.controller;

import com.example.zboruri.domain.Client;
import com.example.zboruri.domain.Flight;
import com.example.zboruri.repository.ClientRepo;
import com.example.zboruri.repository.FlightRepo;
import com.example.zboruri.repository.Repo;
import com.example.zboruri.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Flights {
    @FXML
    TableView<Flight> flightTable;
    @FXML
    TableColumn<Flight,String> fromColumn;
    @FXML
    TableColumn<Flight,String> toColumn;
    @FXML
    TableColumn<Flight, LocalDateTime> departureColumn;
    @FXML
    TableColumn<Flight,LocalDateTime> landingColumn;

    @FXML
    TableColumn<Flight,Integer> sitsColumn;
    @FXML
    ComboBox<String> fromCombo;
    @FXML
    ComboBox<String> toCombo;
    @FXML
    DatePicker departureDate;
    @FXML
    Label messageLabel;

    private String current;
    private Integer index;

    Service srv;

    private String ffrom;
    private String fto;
    private LocalDate fdate;


    ObservableList<Flight> flight = FXCollections.observableArrayList();
    ObservableList<String> from = FXCollections.observableArrayList();
    ObservableList<String> to = FXCollections.observableArrayList();

    public void setService(Service srv, String current)
    {
        this.srv=srv;
        this.current=current;
        fromCombo.setItems(from);
        toCombo.setItems(to);
        // hotelTable.setItems(hotel);

        for(Flight l : srv.getFlights()){
            int ok=1;
            int ok1=1;
            for(String f: from)
                if (Objects.equals(f, l.getFrom())) {
                    ok = 0;
                    break;
                }
            if(ok==1)
                from.add(l.getFrom());
            for(String f: to)
                if (Objects.equals(f, l.getTo())) {
                    ok1 = 0;
                    break;
                }
            if(ok1==1)
                to.add(l.getTo());
        }
        messageLabel.setText("");
        //initModel(flight);
    }

    public void selected() {
        if(fromCombo.getSelectionModel().getSelectedItem()==null || toCombo.getSelectionModel().getSelectedItem()==null || departureDate.getValue()==null)
            return;
        ffrom = fromCombo.getSelectionModel().getSelectedItem();
        fto = toCombo.getSelectionModel().getSelectedItem();
        fdate = departureDate.getValue();

        flight.clear();
        System.out.println(srv.getFlightsFiltered(fdate,ffrom,fto));
        initModel(srv.getFlightsFiltered(fdate,ffrom,fto));
    }

    public void tableSelected(ActionEvent event){
        try {
            Flight f = flightTable.getSelectionModel().getSelectedItem();
            LocalDateTime date = LocalDateTime.now();
            srv.buyTicket(current, f.getFightId(), date);
            messageLabel.setText("succesful");
            initModel(srv.getFlightsFiltered(fdate,ffrom,fto));
        }catch (Exception e)
        {
            messageLabel.setText("error");
        }
    }

    private void initModel(Iterable<Flight> f) {
        List<Flight> flights = StreamSupport.stream(f.spliterator(), false)
                .collect(Collectors.toList());
        flight.setAll(flights);
        messageLabel.setText("");
    }

    @FXML
    public void initialize() {
        fromColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("from"));
        toColumn.setCellValueFactory(new PropertyValueFactory<Flight, String>("to"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departureTime"));
        landingColumn.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landingTime"));
        sitsColumn.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("noSits"));
        flightTable.setItems(flight);
    }

    public void handleNext(ActionEvent event){
        srv.setIndex(srv.getIndex()+5);
        selected();
    }

    public void handlePrevious(ActionEvent event){
        srv.setIndex(srv.getIndex()-5);
        selected();
    }


}
