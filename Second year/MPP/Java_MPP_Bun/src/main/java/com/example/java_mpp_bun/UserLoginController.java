package com.example.java_mpp_bun;

import com.example.java_mpp_bun.FlightsController;
import com.example.java_mpp_bun.domain.User;
import com.example.java_mpp_bun.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserLoginController {
   @FXML
    private TextField usernameTextField;
   @FXML
    private TextField passwordTextField;
   @FXML
   private Label messageLabel;

   Service srv = new Service();

   @FXML
    protected void login(ActionEvent actionEvent){
       try{
           srv.setService();
           String username = usernameTextField.getText();
           String password = passwordTextField.getText();

           User user = srv.login(username,password);
           System.out.println(user);
           usernameTextField.clear();
           passwordTextField.clear();
           FXMLLoader flights = new FXMLLoader(HelloApplication.class.getResource("Flights.fxml"));
           Scene scene = new Scene(flights.load());
           Stage stage = new Stage();
           stage.setTitle("User: "+user.getUsername());
           stage.setScene(scene);
           FlightsController flightsController = flights.getController();
           flightsController.setService(srv);
           stage.show();
           ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
       }catch (Exception e){
           messageLabel.setText(e.getMessage());
           usernameTextField.clear();
           passwordTextField.clear();
       }
   }

}
