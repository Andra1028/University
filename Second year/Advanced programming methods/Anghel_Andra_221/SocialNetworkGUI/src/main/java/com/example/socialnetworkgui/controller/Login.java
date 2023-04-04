package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.HelloApplication;
import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validation.FriendshipValidator;
import com.example.socialnetworkgui.domain.validation.MessageValidator;
import com.example.socialnetworkgui.domain.validation.UserValidator;
import com.example.socialnetworkgui.repository.FriendshipDB;
import com.example.socialnetworkgui.repository.MessageDB;
import com.example.socialnetworkgui.repository.Repository;
import com.example.socialnetworkgui.repository.UserDB;
import com.example.socialnetworkgui.service.ServiceFriendship;
import com.example.socialnetworkgui.service.ServiceMessage;
import com.example.socialnetworkgui.service.ServiceUser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.SortedSet;

public class Login {
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label messageField;

    Repository<Long, User> repou = new UserDB("jdbc:postgresql://localhost:5432/academic", "postgres", "1234", new UserValidator());
    Repository<SortedSet<Long>, Friendship> repof = new FriendshipDB("jdbc:postgresql://localhost:5432/academic", "postgres", "1234", new FriendshipValidator(),repou);

    Repository<Long, Message> repom= new MessageDB("jdbc:postgresql://localhost:5432/academic", "postgres", "1234", new MessageValidator(),repou);
    ServiceUser srvu = new ServiceUser(repou, repof, repom);

    ServiceFriendship srvf= new ServiceFriendship(repou,repof);

    ServiceMessage srvm = new ServiceMessage(repou,repom);
    @FXML
    protected void login(ActionEvent actionEvent) {
        try {
            String email = emailField.getText();
            String password = passwordField.getText();

            User userLogin = srvu.connectUser(email, password);
            emailField.clear();
            passwordField.clear();
            messageField.setText("");
            FXMLLoader userPage = new FXMLLoader(HelloApplication.class.getResource("UserPage.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            stage.setTitle("User:" + userLogin.getFirstName() + " " + userLogin.getLastName());
            stage.setScene(scene);
            UserPage userPageController = userPage.getController();
            userPageController.setService(srvu,srvf,srvm);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            messageField.setText(e.getMessage());
            emailField.clear();
            passwordField.clear();
        }
    }

    @FXML
     protected void onSignUp(ActionEvent actionEvent){
        try{
            FXMLLoader userPage=new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (Exception e) {
            messageField.setText(e.getMessage());        }

    }
}
