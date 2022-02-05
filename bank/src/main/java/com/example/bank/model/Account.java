package com.example.bank.model;

import com.example.bank.controller.AdminScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account extends Crud{

    private int id;
    private BigDecimal balance;
    private String currency;
    private String number;
    private int userfk;

    public Account() {
    }

    public Account(int id, BigDecimal balance, String currency, String number, int userfk) {
        this.id = id;
        this.balance = balance;
        this.currency = currency;
        this.number = number;
        this.userfk = userfk;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getUserfk() {
        return userfk;
    }

    public void setUserfk(int userfk) {
        this.userfk = userfk;
    }

    @Override
    public Object getSingle(int id) {
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT * FROM account WHERE idaccount=?");
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();

            if (rs.next()){
                return new Account(
                        rs.getInt(1),
                        rs.getBigDecimal(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)
                );
            }
            stmnt.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Account se ne moze izvuci iz baze " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean create() {

        return true;
    }

    @Override
    public ObservableList<Object> read() {
        ObservableList<Object> accounts = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = Database.CONNECTION.prepareStatement("select account_number,currency,account_balance from account where user_fk=?");
            statement.setInt(1, AdminScreenController.selectedUser.getIdusers());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Account account = new Account();
                account.setNumber(resultSet.getString(1));
                account.setCurrency(resultSet.getString(2));
                account.setBalance(resultSet.getBigDecimal(3));

                accounts.add(account);
            }
            return accounts;
        }catch (SQLException e){
            System.out.println("Ne mogu dohvatiti racune: "+e);
        }
        return accounts;
    }

    @Override
    public boolean update(Object a,Object b) {
        Account account = (Account) a;
        BigDecimal amount = (BigDecimal) b;
        account.setBalance(account.getBalance().add(amount));
        try{
            PreparedStatement statement = Database.CONNECTION.prepareStatement("update account set account_balance=? where idaccount=?");
            statement.setBigDecimal(1,account.getBalance());
            statement.setInt(2,account.getId());
            statement.execute();
            statement.close();
            return true;
        }catch (SQLException e){
            System.out.println("Greska prilikom update-a accounta!"+e.getMessage());
        }



        return false;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }

    public Account getAccountByNumber(String s){
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT * FROM account WHERE account_number=?");
            stmnt.setString(1, s);
            ResultSet rs = stmnt.executeQuery();

            if (rs.next()){
                return new Account(
                        rs.getInt(1),
                        rs.getBigDecimal(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)
                );
            }
            stmnt.close();
        } catch (SQLException e) {
            System.out.println("Account se ne moze izvuci iz baze " + e.getMessage());
            return null;
        }
        return null;
    }

    public static boolean accountExists(String number){
        try{
            PreparedStatement statement = Database.CONNECTION.prepareStatement("select count(1) from account where account_number=?");
            statement.setString(1,number);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                if(resultSet.getInt(1)==1){
                    return true;
                }
            }
            statement.close();
        }catch (SQLException e){
            System.out.println("Account ne postoji " + e.getMessage());
            return false;
        }
        return false;
    }

}
