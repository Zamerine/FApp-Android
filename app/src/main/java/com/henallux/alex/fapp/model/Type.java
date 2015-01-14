package com.henallux.alex.fapp.model;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Type {
    @com.google.gson.annotations.SerializedName("id")
    private String mId;
    private int id;
    @com.google.gson.annotations.SerializedName("name")
    private String name;
    @com.google.gson.annotations.SerializedName("freezerDuration")
    private Date freezerDuration;
    @com.google.gson.annotations.SerializedName("defaultExpiryDate")
    private Date defaultExpiryDate;

    public Type() {
    }

    public Type(int id, String name, Date freezerDuration, Date defaultExpiryDate) {
        this.id = id;
        this.name = name;
        this.freezerDuration = freezerDuration;
        this.defaultExpiryDate = defaultExpiryDate;
    }

    public Type(int id, String name, Date freezerDuration) {
        this.id = id;
        this.name = name;
        this.freezerDuration = freezerDuration;
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

    public Date getFreezerDuration() {
        return freezerDuration;
    }

    public void setFreezerDuration(Date freezerDuration) {
        this.freezerDuration = freezerDuration;
    }

    public Date getDefaultExpiryDate() {
        return defaultExpiryDate;
    }

    public void setDefaultExpiryDate(Date defaultExpiryDate) {
        this.defaultExpiryDate = defaultExpiryDate;
    }

    @Override
    public String toString() {
        return name;
    }
}
