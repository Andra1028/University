package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.HelloApplication;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validation.ValidationException;
import com.example.socialnetworkgui.service.ServiceFriendship;
import com.example.socialnetworkgui.service.ServiceMessage;
import com.example.socialnetworkgui.service.ServiceUser;
import com.example.socialnetworkgui.utils.FriendshipEntityChangeEvent;
import com.example.socialnetworkgui.utils.Observer;
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

public class FriendRequestPage implements Observer<FriendshipEntityChangeEvent> {


    @FXML
    TableView<User> friendRequestTable;
    @FXML
    TableColumn<User,String> firstnameColumn;
    @FXML
    TableColumn<User,String> lastnameColumn;
    @FXML
    private Label messageLabel;


    ServiceUser srvu ;
    ServiceFriendship srvf;

    ServiceMessage srvm;
    ObservableList<User> model = FXCollections.observableArrayList();
    public void setService(ServiceUser srvu, ServiceFriendship srvf, ServiceMessage srvm) {
        this.srvu = srvu;
        this.srvf = srvf;
        this.srvm = srvm;
        initModel();
    }

    @FXML
    public void initialize() {
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        friendRequestTable.setItems(model);
    }

    private void initModel() {
        Iterable<User> allUsers = srvu.getFriendRequests();
        if(allUsers != null) {
            List<User> users = StreamSupport.stream(allUsers.spliterator(), false)
                    .collect(Collectors.toList());
            model.setAll(users);
        }
    }

    @Override
    public void update(FriendshipEntityChangeEvent friendshipEntityChangeEvent) {
        initModel();
    }

    public void handleAccept(ActionEvent actionEvent) {
        try{
//            messageLabel.setText(srvu.findOne(14L).toString());
            ///Long id1= srvu.getIdCurent();
            User selected= friendRequestTable.getSelectionModel().getSelectedItem();
            ///Long id2= selected.getID();
            srvu.acceptRequest(selected);
            messageLabel.setText("");
            initModel();
        }catch (ValidationException e){
            messageLabel.setText(e.getMessage());
        }
    }

    public void handleDecline(ActionEvent actionEvent) {
        try{
//            messageLabel.setText(srvu.findOne(14L).toString());
            ///Long id1= srvu.getIdCurent();
            User selected= friendRequestTable.getSelectionModel().getSelectedItem();
            ///Long id2= selected.getID();
            srvu.declineRequest(selected);
            messageLabel.setText("");
            initModel();
        }catch (ValidationException e){
            messageLabel.setText(e.getMessage());
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
