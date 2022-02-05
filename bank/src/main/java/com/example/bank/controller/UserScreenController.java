package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.model.AlertHelper;
import com.example.bank.model.Transaction;
import com.example.bank.services.ExchangeRate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.bank.model.User.loggedUser;

public class UserScreenController implements Initializable {
    @FXML
    TextField amountField;
    @FXML
    TextField reciverAcc;

    @FXML
    Button btnSubmitUser;

    @FXML
    TableView<Transaction> TableViewUser;

    @FXML
    TableColumn col1;

    @FXML
    TableColumn col2;

    @FXML
    TableColumn col3;
    @FXML
    TableColumn col4;

    @FXML
    Label yourBalance;

    @FXML
    Button logoutBtn;

    @FXML
    ChoiceBox<String> cbCurrency;

    Transaction transaction = new Transaction();
    String currency=null;
    ObservableList<String> currencies = FXCollections.observableArrayList();

    Utils utils = new Utils();

    public void logout(ActionEvent event){
        utils.showNewWindow("login",event);
    }

    public void submitAction(ActionEvent event){
        Window owner = btnSubmitUser.getScene().getWindow();
        String amount = amountField.getText();
        String reciver = reciverAcc.getText();
        BigDecimal provision = BigDecimal.ZERO;

        if(amount.equals("")){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Unesite sve podatke korektno.");
            return;
        }
        if(reciver.equals("") || currency==null || !isNumeric(amount)){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Unesite sve podatke korektno.");
            return;
        }
        BigDecimal bigDecimalAmount = BigDecimal.valueOf(Double.valueOf(amount));

        if(bigDecimalAmount.compareTo(BigDecimal.ZERO)<=0){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Amount polje ne smije biti manje ili jednako 0!");
            return;
        }

        switch (currency){
            case "EUR":
                bigDecimalAmount = bigDecimalAmount.multiply(ExchangeRate.EUR);
                break;
            case "AUD":
                bigDecimalAmount = bigDecimalAmount.multiply(ExchangeRate.AUD);
                break;
            case "CAD":
                bigDecimalAmount = bigDecimalAmount.multiply(ExchangeRate.CAD);
                break;
            case "USD":
                bigDecimalAmount = bigDecimalAmount.multiply(ExchangeRate.USD);
                break;
            case "RUB":
                bigDecimalAmount = bigDecimalAmount.multiply(ExchangeRate.RUB);
                break;
            case "CNY":
                bigDecimalAmount = bigDecimalAmount.multiply(ExchangeRate.CNY);
                break;
            case "TRY":
                bigDecimalAmount = bigDecimalAmount.multiply(ExchangeRate.TRY);
                break;
            default:
        }

        if(!Account.accountExists(reciver)){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Reciver account does not exist!");
            return;
        }

        Account accountSender = (Account) new Account().getSingle(loggedUser.getIdusers());
        Account accountReciver = new Account().getAccountByNumber(reciver);
        if(accountSender==null){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Sender account not valid!");
            return;
        }

        if(accountSender.getNumber().equals(accountReciver.getNumber())){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Transaction failed!",
                    "Transaction failed to send! Can't send to the same account!");
            return;
        }

        if(accountSender.getBalance().compareTo(bigDecimalAmount)<0){
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Transaction failed!",
                    "Transaction failed to send! Not enough balance!");
            return;
        }


        if(bigDecimalAmount.compareTo(BigDecimal.valueOf(500))<0){
            provision = bigDecimalAmount.multiply(BigDecimal.valueOf(0.012));
        }
        else if(bigDecimalAmount.compareTo(BigDecimal.valueOf(500))>=0){
            provision = bigDecimalAmount.multiply(BigDecimal.valueOf(0.015));
        }

        Transaction transaction = new Transaction(
                0,
                "BAM",
                null,
                reciver,
                accountSender.getNumber(),
                bigDecimalAmount
        );
        boolean isCreated = transaction.create();
        boolean updateAccountReciver = accountReciver.update(accountReciver,bigDecimalAmount.subtract(provision));
        boolean updateAccountSender = accountSender.update(accountSender,bigDecimalAmount.add(provision).negate());

        Account adminAccount = new Account().getAccountByNumber("00000");
        boolean updateAdminProvisionAccount = adminAccount.update(adminAccount,provision);
        if(isCreated && updateAccountReciver && updateAccountSender && updateAdminProvisionAccount){
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Transaction Sent!",
                    "Transaction sent!");
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Transaction failed!",
                    "Transaction failed!");
            return;
        }

        this.fillPage();
    }

    private void fillPage(){
        ObservableList<Transaction> t = (ObservableList<Transaction>) transaction.readForLoggedUser();
        TableViewUser.setItems(t);
        Account accountSender = (Account) new Account().getSingle(loggedUser.getIdusers());
        yourBalance.setText("Your Balance: "+accountSender.getBalance().toString()+ "(BAM)");
        amountField.setText("");
        reciverAcc.setText("");

    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currencies.add("BAM");
        currencies.add("EUR");
        currencies.add("AUD");
        currencies.add("CAD");
        currencies.add("USD");
        currencies.add("RUB");
        currencies.add("CNY");
        currencies.add("TRY");
        cbCurrency.setItems(currencies);
        cbCurrency.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number old, Number newValue) {
                currency=(currencies.get(newValue.intValue()));
            }
        });
        try {
            this.col1.setCellValueFactory(new PropertyValueFactory<>("amount"));
            this.col2.setCellValueFactory(new PropertyValueFactory<>("date"));
            this.col3.setCellValueFactory(new PropertyValueFactory<>("name"));
            this.col4.setCellValueFactory(new PropertyValueFactory<>("reciver"));

            this.fillPage();
        }catch (Exception e){
            System.out.println("Failed to display transactions:"+e.getMessage());
        }
    }
}
