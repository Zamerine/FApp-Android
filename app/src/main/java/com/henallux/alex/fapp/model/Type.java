package com.henallux.alex.fapp.model;

import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Type {
    private int id;
    private String name;
    private Integer freezerDuration;
    private Integer defaultExpiryDate;

    public Type() {
    }

    public Type(int id, String name, Integer freezerDuration, Integer defaultExpiryDate) {
        this.id = id;
        this.name = name;
        this.freezerDuration = freezerDuration;
        this.defaultExpiryDate = defaultExpiryDate;
    }

    public Type(int id, String name, Integer freezerDuration) {
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

    public Integer getFreezerDuration() {
        return freezerDuration;
    }

    public void setFreezerDuration(Integer freezerDuration) {
        this.freezerDuration = freezerDuration;
    }

    public Integer getDefaultExpiryDate() {
        return defaultExpiryDate;
    }

    public void setDefaultExpiryDate(Integer defaultExpiryDate) {
        this.defaultExpiryDate = defaultExpiryDate;
    }

    @Override
    public String toString() {
        return name;
    }
}
