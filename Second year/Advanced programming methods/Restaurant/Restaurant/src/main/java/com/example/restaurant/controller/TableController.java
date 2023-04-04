package com.example.restaurant.controller;

import com.example.restaurant.domain.Order;
import com.example.restaurant.domain.OrderItem;
import com.example.restaurant.service.Service;
import com.example.restaurant.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.MenuItem;
import com.example.restaurant.domain.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TableController implements Observer {

    @FXML
    TableView<MenuItem> antreuTable;
    @FXML
    TableView<MenuItem> principalTable;
    @FXML
    TableView<MenuItem> desertTable;

    @FXML
    TableColumn<MenuItem, String> antreuItemColumn;
    @FXML
    TableColumn<MenuItem, Float> antreuPriceColumn;
    @FXML
    TableColumn<MenuItem, String> antreuCurrencyColumn;
    @FXML
    TableColumn<MenuItem, String> principalItemColumn;
    @FXML
    TableColumn<MenuItem, Float> principalPriceColumn;
    @FXML
    TableColumn<MenuItem, String> principalCurrencyColumn;
    @FXML
    TableColumn<MenuItem, String> desertItemColumn;
    @FXML
    TableColumn<MenuItem, Float> desertPriceColumn;
    @FXML
    TableColumn<MenuItem, String> desertCurrencyColumn;


    Service srv;
    private Integer id;
    private Iterable<MenuItem> a;
    private Iterable<MenuItem> p;
    private Iterable<MenuItem> d;

    ObservableList<MenuItem> modelAntreu = FXCollections.observableArrayList();
    ObservableList<MenuItem> modelPrincipal = FXCollections.observableArrayList();
    ObservableList<MenuItem> modelDesert = FXCollections.observableArrayList();
    public void setService(Service srv, Integer id)
    {
        this.srv=srv;
        this.id=id;
        a= srv.getItemsByCategory("Antreu");
        p= srv.getItemsByCategory("Fel principal");
        d= srv.getItemsByCategory("Desert");
        srv.addObserver(this);
        initModel(a,p,d);

    }

    private void initModel(Iterable<MenuItem> a, Iterable<MenuItem> p, Iterable<MenuItem> d) {
        List<MenuItem> antreu = StreamSupport.stream(a.spliterator(), false)
                .collect(Collectors.toList());
        modelAntreu.setAll(antreu);
        List<MenuItem> principal = StreamSupport.stream(p.spliterator(), false)
                .collect(Collectors.toList());
        modelPrincipal.setAll(principal);
        List<MenuItem> desert = StreamSupport.stream(d.spliterator(), false)
                .collect(Collectors.toList());
        modelDesert.setAll(desert);

    }

    @FXML
    public void initialize() {

        desertTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        principalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        antreuTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        antreuItemColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("item"));
        antreuPriceColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Float>("price"));
        antreuCurrencyColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("currency"));
        antreuTable.setItems(modelAntreu);
        principalItemColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("item"));
        principalPriceColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Float>("price"));
        principalCurrencyColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("currency"));
        principalTable.setItems(modelPrincipal);
        desertItemColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("item"));
        desertPriceColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, Float>("price"));
        desertCurrencyColumn.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("currency"));
        desertTable.setItems(modelDesert);

    }

    public void handleOrder(ActionEvent actionEvent) {
        try{
            Iterable<MenuItem> antreu=antreuTable.getSelectionModel().getSelectedItems();
            Iterable<MenuItem> principal=principalTable.getSelectionModel().getSelectedItems();
            Iterable<MenuItem> desert=desertTable.getSelectionModel().getSelectedItems();


            int orderId= srv.calutateOrderId();
            LocalDateTime d =LocalDateTime.now();
            Order o = new Order(orderId, id,d,Order.stringToType("PLACED"));
            srv.saveOrder(o);
            for(MenuItem m: antreu) {
                OrderItem oi= new OrderItem(orderId,m.getId());
                srv.saveOrderItem(oi);
            }
            for(MenuItem m: principal) {
                OrderItem oi= new OrderItem(orderId,m.getId());
                srv.saveOrderItem(oi);
            }
            for(MenuItem m: desert) {
                OrderItem oi= new OrderItem(orderId,m.getId());
                srv.saveOrderItem(oi);
            }
            srv.notifyObservers();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void update (){initModel(a,p,d);}
}
