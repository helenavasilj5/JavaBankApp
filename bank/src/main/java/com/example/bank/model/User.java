package com.example.bank.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class User extends Crud{

    public User() {
    }

    private int idusers;
    private String name;
    private String username;
    private String role;
    private String email;
    private String password;

    public static User loggedUser=null;


    public User(int idusers, String name, String username, String role, String email, String password) {
        this.idusers = idusers;
        this.name = name;
        this.username = username;
        this.role = role;
        this.email = email;
        this.password = password;
    } // prilikom stvaranja objekta puni mu polja, ovaj konstruktor

    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public LogLogin set(String successful){
        try {
            return new LogLogin(0, loggedUser.getName(), loggedUser.getUsername(), loggedUser.getRole(), loggedUser.getEmail(), successful);
        }catch (Exception e){
            System.out.println("Tu je greska: " + e.getMessage());
            return null;
        }
    }


    @Override
    public Object getSingle(int id) {
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT * FROM users WHERE idusers=?");
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();

            if (rs.next()){
                return new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
            stmnt.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Korisnik se ne moze izvuci iz baze " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean create() {

        return true;
    }

    @Override
    public ObservableList<Object> read() {
        ObservableList<Object> objectObservableList = FXCollections.observableArrayList();
        try{
            PreparedStatement preparedStatement = Database.CONNECTION.prepareStatement("select * from users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                User user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
                objectObservableList.add(user);
            }
        return objectObservableList;
        }catch(SQLException e){
            System.out.println("Neuspjesno dohvaćanje usera:"+e.getMessage());
        }
        return null;
    }

    @Override
    public boolean update(Object o,Object b) {
        return false;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }
}
