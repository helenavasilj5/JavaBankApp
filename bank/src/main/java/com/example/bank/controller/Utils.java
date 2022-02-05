package com.example.bank.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Utils {
    public void showNewWindow(String viewname, ActionEvent e) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/bank/"+viewname+".fxml"));
            loader.load();
            Parent root = loader.getRoot();
            Scene scene = new Scene (root,800,400);
            stage.setTitle(viewname);
            stage.setScene(scene);
            ((Node)(e.getSource())).getScene().getWindow().hide();
            stage.show();
        } catch (Exception ex) {
            System.out.println("Nastala je greška: " + ex.getMessage());
        }

    }
}
