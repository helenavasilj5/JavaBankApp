package com.example.bank.controller;

import com.example.bank.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminScreenController implements Initializable {
    @FXML
    TableView tbl;
    @FXML
    TableColumn col1;
    @FXML
    TableColumn col2;
    @FXML
    TableColumn col3;
    @FXML
    TableColumn col4;
    @FXML
    TableColumn col5;

    @FXML
    Button btn;

    @FXML
    Button btn2;

    @FXML
    Button btnLoginHistory;




    Utils utils = new Utils();

    public static User selectedUser;


    User user = new User();
    private void fillPage(){
        ObservableList<Object> t = (ObservableList<Object>) user.read();
        tbl.setItems(t);
    }

    public void checkUser(ActionEvent event){

        selectedUser = (User)tbl.getSelectionModel().getSelectedItem();
        if(selectedUser==null){
            return;
        }
        utils.showNewWindow("AdminCheckUser",event);
    }
    @FXML
    public void logout(ActionEvent event){
        utils.showNewWindow("login",event);
    }

    @FXML
    public void loginHistory(ActionEvent event){
        utils.showNewWindow("LogLoginScreen",event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col2.setCellValueFactory(new PropertyValueFactory<>("username"));
        col3.setCellValueFactory(new PropertyValueFactory<>("role"));
        col4.setCellValueFactory(new PropertyValueFactory<>("email"));
        col5.setCellValueFactory(new PropertyValueFactory<>("idusers"));

        fillPage();

    }
}
