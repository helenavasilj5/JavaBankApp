package com.example.bank.model;

import com.example.bank.controller.AdminCheckUserController;
import com.example.bank.controller.AdminScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;

import static com.example.bank.model.User.loggedUser;

public class Transaction extends Crud{

    private int id;
    private String currency;
    private Timestamp date;
    private String reciver;
    private String sender;
    private BigDecimal amount;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", date=" + date +
                ", reciver='" + reciver + '\'' +
                ", sender='" + sender + '\'' +
                ", amount=" + amount +
                '}';
    }

    public Transaction() {
    }

    public Transaction(int id, String currency, Timestamp date, String reciver, String sender, BigDecimal amount) {
        this.id = id;
        this.currency = currency;
        this.date = date;
        this.reciver = reciver;
        this.sender = sender;
        this.amount = amount;
    }


    @Override
    public Transaction getSingle(int id) {
        try {
            PreparedStatement stmnt = Database.CONNECTION.prepareStatement("SELECT * FROM transaction WHERE idtransaction=?");
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();

            if (rs.next()){
                return new Transaction(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getTimestamp(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBigDecimal(6)
                );
            }
            stmnt.close();
            return null;
        } catch (SQLException e) {
            System.out.println("Transakcija se ne moze izvuci iz baze " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean create() {
        try{
            PreparedStatement statement = Database.CONNECTION.prepareStatement("insert into transaction values(0,?,default,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1,this.currency);
            statement.setString(2,this.reciver);
            statement.setString(3,this.sender);
            statement.setBigDecimal(4,this.amount);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Nisam uspio dodati transakciju: " + e.getMessage());
        }
        return false;
    }

    @Override
    public ObservableList<Object> read() {
        ObservableList<Object> transactions = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = Database.CONNECTION.prepareStatement("select * from transaction where idtransaction=?");
            statement.setInt(1,loggedUser.getIdusers());

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt(1));
                transaction.setCurrency(resultSet.getString(2));
                transaction.setDate(resultSet.getTimestamp(3));
                transaction.setReciver(resultSet.getString(4));
                transaction.setSender(resultSet.getString(5));
                transaction.setAmount(resultSet.getBigDecimal(6));
                transactions.add(transaction);
            }
            statement.close();
            System.out.println(transactions.get(0).toString());
            return transactions;
        }catch (SQLException e){
            System.out.println("Nisam uspio izvuci transakcije iz baze: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Object o, Object b) {
        return false;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }



    public ObservableList<Transaction> readForLoggedUser() {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        try{

            PreparedStatement statement = Database.CONNECTION.prepareStatement("select idtransaction,currency_of_transaction,date_of_transaction,reciver_acc_number,sender_acc_number,amount from transaction,account where user_fk=? and sender_acc_number=account_number");
            statement.setInt(1,loggedUser.getIdusers());

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setId(resultSet.getInt(1));
                transaction.setCurrency(resultSet.getString(2));
                transaction.setDate(resultSet.getTimestamp(3));
                transaction.setReciver(resultSet.getString(4));
                transaction.setSender(resultSet.getString(5));
                transaction.setAmount(resultSet.getBigDecimal(6));

                PreparedStatement statement1 = Database.CONNECTION.prepareStatement("select name from users,account where account_number=? and idusers=user_fk");
                statement1.setString(1,transaction.getReciver());
                ResultSet resultSet1 = statement1.executeQuery();
                if(resultSet1.next()){
                    transaction.setName(resultSet1.getString(1));
                }
                transactions.add(transaction);
                statement1.close();
            }
            statement.close();

            return transactions;
        }catch (SQLException e){
            System.out.println("Nisam uspio izvuci transakcije iz baze: " + e.getMessage());
            return transactions;
        }
    }


    public static ObservableList<Transaction> getTransactionsForSelectedUser(){
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        try{
            PreparedStatement statement = Database.CONNECTION.prepareStatement("select currency_of_transaction,date_of_transaction,reciver_acc_number,amount from transaction,account,users where reciver_acc_number=account_number and idusers=user_fk and idusers=?");
            statement.setInt(1, AdminScreenController.selectedUser.getIdusers());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction();
                transaction.setCurrency(resultSet.getString(1));
                transaction.setDate(resultSet.getTimestamp(2));
                transaction.setReciver(resultSet.getString(3));
                transaction.setAmount(resultSet.getBigDecimal(4));

                transactions.add(transaction);
            }

        return transactions;
        }catch (SQLException e){
            System.out.println("Greska u dohvatu usera:"+e);
        }



        return transactions;
    }



}
