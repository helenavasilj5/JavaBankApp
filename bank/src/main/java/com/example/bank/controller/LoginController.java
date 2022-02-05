package com.example.bank.controller;

import com.example.bank.model.User;
import com.example.bank.model.AlertHelper;
import com.example.bank.model.Database;
import com.example.bank.model.LogLogin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.bank.model.User.loggedUser;

public class LoginController implements Initializable {
    @FXML
    Button btnLogin;

    @FXML
    TextField usernameF;

    @FXML
    PasswordField passwordF;


@FXML
    protected void login(ActionEvent event){
        String username = usernameF.getText();
        String password = passwordF.getText();
        Window window = btnLogin.getScene().getWindow();
        if(username.isEmpty()){
            AlertHelper.showAlert(Alert.AlertType.ERROR,window,"Form error","Please Enter username!");
            return;
        }
        if(password.isEmpty()){
            AlertHelper.showAlert(Alert.AlertType.ERROR,window,"Form error","Please Enter password!");
            return;
        }

        try{
            User.loggedUser = null;
            PreparedStatement statement = Database.CONNECTION.prepareStatement("select * from users where username=? and password=?");
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet rs = statement.executeQuery();
            User user = new User();
            LogLogin logLogin = new LogLogin();
            if(rs.next()){
                loggedUser = (User) user.getSingle(rs.getInt(1));
                Utils utils = new Utils();
                assert loggedUser != null;
                if(loggedUser.getRole().equals("USER")){
                    utils.showNewWindow("UserScreen",event);
                    logLogin = loggedUser.set("Y");
                    logLogin.create();
                }
                else if(loggedUser.getRole().equals("ADMIN")){
                    utils.showNewWindow("AdminScreen",event);
                    logLogin = loggedUser.set("Y");
                    logLogin.create();
                }
            }
            else{
                logLogin.setUsername(username);
                logLogin.setName("");
                logLogin.setEmail("");
                logLogin.setRole("");
                logLogin.setSuccessful("N");
                logLogin.create();
                AlertHelper.showAlert(Alert.AlertType.ERROR,window,"Form error!","Input data is incorrect!");
                return;
            }
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
