package com.henallux.alex.fapp.model;

import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Item {
    private int id;
    private String name;
    private GregorianCalendar expiryDate;
    private int quantity;
    private Type type;
    private GregorianCalendar lastSync;

    public Item() {
    }

    public Item(int id, String name, GregorianCalendar expiryDate, int quantity, Type type,
                GregorianCalendar lastSync) {
        this.id = id;
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.type = type;
        this.lastSync = lastSync;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public GregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(GregorianCalendar expiryDate) {
        this.expiryDate = expiryDate;
    }

    public GregorianCalendar getLastSync() {
        return lastSync;
    }

    public void setLastSync(GregorianCalendar lastSync) {
        this.lastSync = lastSync;
    }
}
