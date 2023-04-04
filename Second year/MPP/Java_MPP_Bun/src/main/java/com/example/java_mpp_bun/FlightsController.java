package com.example.java_mpp_bun;

import com.example.java_mpp_bun.domain.User;
import com.example.java_mpp_bun.domain.Zbor;
import com.example.java_mpp_bun.service.Service;
import com.example.java_mpp_bun.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FlightsController implements Observer {
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
    private TextField destinationField;
    @FXML
    private DatePicker dateField;
    @FXML
    private Label messageLabel;

    Service srv;

    ObservableList<Zbor> model = FXCollections.observableArrayList();

    public void setService (Service srv){
        this.srv=srv;
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
        Iterable<Zbor> zboruri = srv.findAllFlignts();
        System.out.println(zboruri);
        List<Zbor> zbor = StreamSupport.stream(zboruri.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(zbor);
    }

    @Override
    public void update (){initModel();}

    @FXML
    protected void find(ActionEvent actionEvent) {
        try {
            srv.setService();
            LocalDate date = dateField.getValue();
            String dest = destinationField.getText();

            FXMLLoader flights = new FXMLLoader(HelloApplication.class.getResource("FindFlight.fxml"));
            Scene scene = new Scene(flights.load());
            Stage stage = new Stage();
            stage.setTitle(dest + date.toString());
            stage.setScene(scene);
            FindFlightsController flightsController = flights.getController();
            flightsController.setService(srv,dest,date);
            stage.show();
            //((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            destinationField.clear();
        }
    }

}
