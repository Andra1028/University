module com.example.java_mpp_bun {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;


    opens com.example.java_mpp_bun to javafx.fxml;
    exports com.example.java_mpp_bun;
    exports com.example.java_mpp_bun.domain;
    exports com.example.java_mpp_bun.repository;
    exports com.example.java_mpp_bun.service;
    exports com.example.java_mpp_bun.utils;
}