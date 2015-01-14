package com.henallux.alex.fapp.model;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Item {

    @com.google.gson.annotations.SerializedName("id")
    private String mId;
    private Integer id;
    @com.google.gson.annotations.SerializedName("name")
    private String name;
    @com.google.gson.annotations.SerializedName("expiryDate")
    private Date expiryDate;
    @com.google.gson.annotations.SerializedName("quantity")
    private int quantity;
    @com.google.gson.annotations.SerializedName("type")
    private Type type;
    @com.google.gson.annotations.SerializedName("lastSync")
    private Date lastSync;

    public Item() {
    }

    public Item(Integer id, String name, Date expiryDate, int quantity, Type type,
                Date lastSync) {
        this.id = id;
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.type = type;
        this.lastSync = lastSync;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }

    public String getmId() {return mId;}

    public void setmId(String mId) {this.mId = mId;    }
}
