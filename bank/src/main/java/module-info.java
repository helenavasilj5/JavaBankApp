module com.example.bank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bank to javafx.fxml;
    opens com.example.bank.model to javafx.base;
    exports com.example.bank;
    exports com.example.bank.controller;
    opens com.example.bank.controller to javafx.fxml;
}