package com.example.bank.controller;

import com.example.bank.model.AlertHelper;
import com.example.bank.model.LogLogin;
import com.example.bank.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class LogLoginController implements Initializable {

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
    TableColumn col6;
    @FXML
    Button delbtn;

    @FXML
    Button btnback;





    public void back(ActionEvent event) {
        utils.showNewWindow("AdminScreen",event);
    }

    LogLogin logLogin = new LogLogin();
    Utils utils = new Utils();



    public void delete(ActionEvent event){
        Window owner = delbtn.getScene().getWindow();
        logLogin=null;
        logLogin = (LogLogin) tbl.getSelectionModel().getSelectedItem();
        if(logLogin==null){
            return;
        }
        if(logLogin.delete(logLogin)){
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Deleted!",
                    "Deleted!");
            fillTables();
        }


    }
    void fillTables() {
        ObservableList<Object> a = (ObservableList<Object>) logLogin.read();
        tbl.setItems(a);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3.setCellValueFactory(new PropertyValueFactory<>("username"));
        col4.setCellValueFactory(new PropertyValueFactory<>("role"));
        col5.setCellValueFactory(new PropertyValueFactory<>("email"));
        col6.setCellValueFactory(new PropertyValueFactory<>("successful"));
        fillTables();

    }
}
