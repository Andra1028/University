package com.example.restaurant;

import com.example.restaurant.controller.StaffController;
import com.example.restaurant.controller.TableController;
import com.example.restaurant.domain.MenuItem;
import com.example.restaurant.domain.Order;
import com.example.restaurant.domain.OrderItem;
import com.example.restaurant.domain.Table;
import com.example.restaurant.repository.*;
import com.example.restaurant.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    Repo<Table> repot = new TableRepo("jdbc:postgresql://localhost:5432/restaurant", "postgres", "1234");

    Repo<MenuItem> repom = new MenuItemRepo("jdbc:postgresql://localhost:5432/restaurant", "postgres", "1234");
    Repo<Order> repoo = new OrderRepo("jdbc:postgresql://localhost:5432/restaurant", "postgres", "1234");

    Repo<OrderItem> repooi = new OrderItemRepo("jdbc:postgresql://localhost:5432/restaurant", "postgres", "1234");

    Service srv = new Service(repot,repom,repoo,repooi);
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("StaffPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Staff");
        stage.setScene(scene);
       StaffController staffController = fxmlLoader.getController();
        staffController.setService(srv);
        stage.show();
        for (Table t: srv.getTables())
            getTable(t.getId());
    }

    public void getTable(Integer id) {

        for (Table t : srv.getTables()) {
            //System.out.println(c.getClientName());
            if (Objects.equals(id, t.getId())) {
                try {
                    ///System.out.println(c.getClientName());
                    Stage stageTable = new Stage();
                    // System.out.println(c.getName());
                    FXMLLoader fxmlLoaderClient = new FXMLLoader(HelloApplication.class.getResource("TablePage.fxml"));
                    Scene scene = new Scene(fxmlLoaderClient.load());
                    stageTable.setTitle("Table" + t.getId());
                    stageTable.setScene(scene);
                    TableController tableController = fxmlLoaderClient.getController();
                    tableController.setService(srv,t.getId());
                    stageTable.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}