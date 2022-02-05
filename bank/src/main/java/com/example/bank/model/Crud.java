package com.example.bank.model;

import javafx.collections.ObservableList;

import java.math.BigDecimal;

public abstract class Crud {

    public abstract Object getSingle(int id);

    public abstract boolean create();

    public abstract ObservableList<Object> read();

    public abstract boolean update(Object o, Object b);

    public abstract boolean delete(Object o);





}
