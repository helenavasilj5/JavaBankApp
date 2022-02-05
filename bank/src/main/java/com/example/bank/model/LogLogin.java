package com.example.bank.model;

import com.example.bank.controller.AdminScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogLogin extends Crud{

    private int id;
    private String name;
    private String username;
    private String role;
    private String email;
    private String successful;

    public LogLogin() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSuccessful() {
        return successful;
    }

    public void setSuccessful(String successful) {
        this.successful = successful;
    }


    public LogLogin(int id, String name, String username, String role, String email, String successful) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.role = role;
        this.email = email;
        this.successful = successful;
    }

    @Override
    public Object getSingle(int id) {
        return null;
    }

    @Override
    public boolean create() {
        try{
            PreparedStatement statement = Database.CONNECTION.prepareStatement("INSERT into loglogin values (0, ?, ?, ?, ?, ?)");
            statement.setString(1,this.name);
            statement.setString(2,this.username);
            statement.setString(3,this.role);
            statement.setString(4,this.email);
            statement.setString(5,this.successful);
            statement.execute();

            statement.close();
            return true;
        }catch(SQLException e){
            System.out.println("Nisam uspio dodati loglogin: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ObservableList<Object> read() {
        ObservableList<Object> loglogins = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = Database.CONNECTION.prepareStatement("select * from loglogin");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                LogLogin logLogin = new LogLogin();
                logLogin.setId(resultSet.getInt(1));
                logLogin.setName(resultSet.getString(2));
                logLogin.setUsername(resultSet.getString(3));
                logLogin.setRole(resultSet.getString(4));
                logLogin.setEmail(resultSet.getString(5));
                logLogin.setSuccessful(resultSet.getString(6));

                loglogins.add(logLogin);
            }
            return loglogins;
        }catch (SQLException e){
            System.out.println("Ne mogu dohvatiti loglogine: "+e);
        }
        return loglogins;
    }

    @Override
    public boolean update(Object o, Object b) {

        return false;
    }

    @Override
    public boolean delete(Object o) {
        LogLogin logLogin = (LogLogin) o;
        try{
            PreparedStatement statement = Database.CONNECTION.prepareStatement("DELETE FROM loglogin WHERE idloglogin=?");
            statement.setInt(1,logLogin.id);
            statement.execute();
            return true;
        }catch (SQLException e){
            System.out.println("Ne mogu izbrisati loglogin:"+e);
        }
        return false;
    }
}
