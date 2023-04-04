package com.example.restaurant.controller;

import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Order;
import com.example.restaurant.domain.OrderItem;
import com.example.restaurant.service.Service;
import com.example.restaurant.utils.Observer;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StaffController implements Observer{
    @FXML
    TableView<Order> placedTable;
    @FXML
    TableColumn<Order,Integer> tableColumn;
    @FXML
    TableColumn<Order, LocalDateTime> dateColumn;
    @FXML
    TableColumn<Order,String> itemsColumn;

    Service srv;

    ObservableList<Order> model = FXCollections.observableArrayList();

    public void setService (Service srv)
    {
        this.srv=srv;
        srv.addObserver(this);
        initModel();
    }

    private void initModel() {
        Iterable<Order> orders = srv.getOrders();
        List<Order> ord = StreamSupport.stream(orders.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(ord);

    }

    @FXML
    public void initialize() {
        tableColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("table"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Order, LocalDateTime>("date"));
        itemsColumn.setCellValueFactory(c -> {
            Order o=c.getValue();
            String rez="";
            Iterable<OrderItem> oi = srv.findByOrderId(o.getId());
            for(OrderItem or: oi)
            {
                MenuItem m=srv.findById(or.getItem());
                rez= rez + m.getItem() +  ", ";
            }
            return new ReadOnlyObjectWrapper<String>(rez);
});
        placedTable.setItems(model);
    }

    @Override
    public void update (){initModel();}

}
