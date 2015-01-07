package com.henallux.alex.fapp.model;

import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Type {
    private int id;
    private String name;
    private GregorianCalendar freezerDuration;
    private GregorianCalendar defaultExpiryDate;

    public Type() {
    }

    public Type(int id, String name, GregorianCalendar freezerDuration, GregorianCalendar defaultExpiryDate) {
        this.id = id;
        this.name = name;
        this.freezerDuration = freezerDuration;
        this.defaultExpiryDate = defaultExpiryDate;
    }

    public Type(int id, String name, GregorianCalendar freezerDuration) {
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

    public GregorianCalendar getFreezerDuration() {
        return freezerDuration;
    }

    public void setFreezerDuration(GregorianCalendar freezerDuration) {
        this.freezerDuration = freezerDuration;
    }

    public GregorianCalendar getDefaultExpiryDate() {
        return defaultExpiryDate;
    }

    public void setDefaultExpiryDate(GregorianCalendar defaultExpiryDate) {
        this.defaultExpiryDate = defaultExpiryDate;
    }
}
