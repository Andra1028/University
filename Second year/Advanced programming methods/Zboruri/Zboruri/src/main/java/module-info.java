module com.example.zboruri {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.zboruri to javafx.fxml;
    exports com.example.zboruri;
    exports com.example.zboruri.controller;
    exports com.example.zboruri.domain;
    exports com.example.zboruri.repository;
    exports com.example.zboruri.service;
    opens com.example.zboruri.controller;
}