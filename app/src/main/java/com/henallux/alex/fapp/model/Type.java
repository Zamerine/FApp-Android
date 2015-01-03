package com.henallux.alex.fapp.model;

import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Type {
    private String id;
    private String name;
    private GregorianCalendar freezerDuration;
    private GregorianCalendar defaultExpireDate;

    public Type() {
    }

    public Type(String id, String name, GregorianCalendar freezerDuration, GregorianCalendar defaultExpireDate) {
        this.id = id;
        this.name = name;
        this.freezerDuration = freezerDuration;
        this.defaultExpireDate = defaultExpireDate;
    }

    public Type(String id, String name, GregorianCalendar freezerDuration) {
        this.id = id;
        this.name = name;
        this.freezerDuration = freezerDuration;
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

    public GregorianCalendar getFreezerDuration() {
        return freezerDuration;
    }

    public void setFreezerDuration(GregorianCalendar freezerDuration) {
        this.freezerDuration = freezerDuration;
    }

    public GregorianCalendar getDefaultExpireDate() {
        return defaultExpireDate;
    }

    public void setDefaultExpireDate(GregorianCalendar defaultExpireDate) {
        this.defaultExpireDate = defaultExpireDate;
    }
}
