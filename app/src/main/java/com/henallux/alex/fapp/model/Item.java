package com.henallux.alex.fapp.model;

import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Item {
    private String id;
    private String name;
    private GregorianCalendar expiryDate;
    private int quantity;
    private Type type;

    public Item() {
    }

    public Item(String id, String name, GregorianCalendar expiryDate, int quantity, Type type) {
        this.id = id;
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
