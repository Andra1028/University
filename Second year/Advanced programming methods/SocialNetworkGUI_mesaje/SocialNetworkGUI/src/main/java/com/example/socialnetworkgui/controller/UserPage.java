package com.example.socialnetworkgui.controller;

import com.example.socialnetworkgui.HelloApplication;
import com.example.socialnetworkgui.domain.Friendship;
import com.example.socialnetworkgui.domain.User;
import com.example.socialnetworkgui.domain.validation.FriendshipValidator;
import com.example.socialnetworkgui.domain.validation.UserValidator;
import com.example.socialnetworkgui.domain.validation.ValidationException;
import com.example.socialnetworkgui.repository.FriendshipDB;
import com.example.socialnetworkgui.repository.Repository;
import com.example.socialnetworkgui.repository.UserDB;
import com.example.socialnetworkgui.service.ServiceFriendship;
import com.example.socialnetworkgui.service.ServiceMessage;
import com.example.socialnetworkgui.service.ServiceUser;
import com.example.socialnetworkgui.utils.FriendshipEntityChangeEvent;
import com.example.socialnetworkgui.utils.Observer;
import com.example.socialnetworkgui.utils.UserEntityChangeEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserPage implements Observer<FriendshipEntityChangeEvent> {

    @FXML
    TableView<User> friendshipsTable;
    @FXML
    TableColumn<User,String> firstnameColumn;
    @FXML
    TableColumn<User,String> lastnameColumn;
    @FXML
    private Label messageLabel;

    Repository<Long, User> repou = new UserDB("jdbc:postgresql://localhost:5432/academic", "postgres", "1234", new UserValidator());
    Repository<SortedSet<Long>, Friendship> repof = new FriendshipDB("jdbc:postgresql://localhost:5432/academic", "postgres", "1234", new FriendshipValidator(),repou);
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
        friendshipsTable.setItems(model);
    }

    private void initModel() {
        Iterable<User> allUsers = srvu.getFriends();
        List<User> users = StreamSupport.stream(allUsers.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(users);

    }

    @Override
    public void update(FriendshipEntityChangeEvent friendshipEntityChangeEvent) {
        initModel();
    }

    public void handleDelete(ActionEvent actionEvent) {
        try{
//            messageLabel.setText(srvu.findOne(14L).toString());
            Long id1= srvu.getIdCurent();
            User selected= friendshipsTable.getSelectionModel().getSelectedItem();
            Long id2= selected.getID();
            srvf.deleteFriend(id1, id2);
            messageLabel.setText("");
            initModel();
        }catch (ValidationException e){
            messageLabel.setText(e.getMessage());
        }
    }

    public void handleAdd(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader userPage=new FXMLLoader(HelloApplication.class.getResource("AddFriendPage.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            AddFriendPage friendPageController = userPage.getController();
            friendPageController.setService(srvu,srvf,srvm);
            stage.show();
            initModel();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (Exception e) {
            messageLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void handleRequest(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader userPage=new FXMLLoader(HelloApplication.class.getResource("FriendRequestPage.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            FriendRequestPage friendRequestController = userPage.getController();
            friendRequestController.setService(srvu,srvf,srvm);
            stage.show();
            initModel();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (Exception e) {
            messageLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void handleSent(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader userPage=new FXMLLoader(HelloApplication.class.getResource("SentFriendRequestPage.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            SentFriendRequestPage sentfriendRequestController = userPage.getController();
            sentfriendRequestController.setService(srvu,srvf,srvm);
            stage.show();
            initModel();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (Exception e) {
            messageLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void handleSendMessage(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader userPage=new FXMLLoader(HelloApplication.class.getResource("MessagePage.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            MessagePage sentfriendRequestController = userPage.getController();
            sentfriendRequestController.setService(srvu,srvf, srvm);
            stage.show();
            initModel();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (Exception e) {
            messageLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void handleSentMessages(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader userPage=new FXMLLoader(HelloApplication.class.getResource("SentMessagesPage.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            SentMessagesPage sentfriendRequestController = userPage.getController();
            sentfriendRequestController.setService(srvu,srvf, srvm);
            stage.show();
            initModel();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (Exception e) {
            messageLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void handleReceivedMessages(ActionEvent actionEvent) throws IOException {
        try{
            FXMLLoader userPage=new FXMLLoader(HelloApplication.class.getResource("ReceivedMessagesPage.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            ReceivedMessagesPage sentfriendRequestController = userPage.getController();
            sentfriendRequestController.setService(srvu,srvf, srvm);
            stage.show();
            initModel();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (Exception e) {
            messageLabel.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void handleBack(ActionEvent actionEvent) {
        try{
            FXMLLoader userPage = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(userPage.load());
            Stage stage = new Stage();
            ///stage.setTitle("User:" + userLogin.getFirstName() + " " + userLogin.getLastName());
            stage.setScene(scene);
            Login loginController = userPage.getController();
            ///loginController.setService(srvu,srvf);
            stage.show();
            initModel();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        }catch (ValidationException | IOException e){
            messageLabel.setText(e.getMessage());
        }
    }

}
