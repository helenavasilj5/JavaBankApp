package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.model.Transaction;
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

public class AdminCheckUserController implements Initializable {

    Utils utils = new Utils();


    @FXML
    Button backBtn;
    @FXML
    public void back(ActionEvent event){
        utils.showNewWindow("AdminScreen",event);
    }

    @FXML
    TableView accTable;
    @FXML
    TableView transTable;
    @FXML
    TableColumn transCurrency;
    @FXML
    TableColumn transDate;
    @FXML
    TableColumn transReciver;
    @FXML
    TableColumn transAmount;
    @FXML
    TableColumn accNumber;
    @FXML
    TableColumn accBalance;
    @FXML
    TableColumn accCurrency;


    Account account = new Account();
    Transaction transaction = new Transaction();
    void fillTables(){
        ObservableList<Object> a = (ObservableList<Object>) account.read();
        accTable.setItems(a);

        ObservableList<Transaction> t = (ObservableList<Transaction>) transaction.getTransactionsForSelectedUser();
        transTable.setItems(t);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        transCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        transDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        transReciver.setCellValueFactory(new PropertyValueFactory<>("reciver"));

        accBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        accCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        accNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        fillTables();



    }
}
