package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.HelloApplication;
import com.example.socialnetworkgui.domain.Message;
import com.example.socialnetworkgui.domain.validation.ValidationException;
import com.example.socialnetworkgui.service.ServiceFriendship;
import com.example.socialnetworkgui.service.ServiceMessage;
import com.example.socialnetworkgui.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SentMessagesPage {


    @FXML
    TableView<Message> messTable;
    @FXML
    TableColumn<Message, String> firstnameColumn;
    @FXML
    TableColumn<Message, String> lastnameColumn;
    @FXML
    TableColumn<Message,String> messageColumn;
    @FXML
    private Label messageLabel;

    ServiceUser srvu ;
    ServiceFriendship srvf;

    ServiceMessage srvm;
    ObservableList<Message> model = FXCollections.observableArrayList();
    public void setService(ServiceUser srvu, ServiceFriendship srvf, ServiceMessage srvm) {
        this.srvu = srvu;
        this.srvf = srvf;
        this.srvm = srvm;
        initModel();
    }

    @FXML
    public void initialize() {
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("user2Firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("user2Lastname"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("message"));
        messTable.setItems(model);
    }

    private void initModel() {
        Iterable<Message> allUsers = srvu.sentMessages();
        if(allUsers != null) {
            List<Message> users = StreamSupport.stream(allUsers.spliterator(), false)
                    .collect(Collectors.toList());
            model.setAll(users);
        }
    }




    public void handleBack(ActionEvent actionEvent) {
        try{
            FXMLLoader userPage = new FXMLLoader(HelloApplication.class.getResource("UserPage.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            ///stage.setTitle("User:" + userLogin.getFirstName() + " " + userLogin.getLastName());
            stage.setScene(scene);
            UserPage userPageController = userPage.getController();
            userPageController.setService(srvu,srvf,srvm);
            stage.show();
            initModel();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (ValidationException | IOException e){
            messageLabel.setText(e.getMessage());
        }
    }
}
