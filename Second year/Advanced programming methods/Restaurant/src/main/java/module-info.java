module com.example.restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.restaurant to javafx.fxml;
    exports com.example.restaurant;
    exports com.example.restaurant.service;
    exports com.example.restaurant.repository;
    exports com.example.restaurant.domain;
    exports com.example.restaurant.controller;
    opens com.example.restaurant.controller;
}