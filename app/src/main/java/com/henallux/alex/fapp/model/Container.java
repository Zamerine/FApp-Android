package com.henallux.alex.fapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Container {
    public static final int TYPE_FRIGO = 0;
    public static final int TYPE_FREEZER = 1;

    @com.google.gson.annotations.SerializedName("id")
    private String mId;
    private Integer id;
    @com.google.gson.annotations.SerializedName("name")
    private String name;
    @com.google.gson.annotations.SerializedName("lastSync")
    private Date lastSync;
    private ArrayList<Item> items;
    @com.google.gson.annotations.SerializedName("type")
    private int type;

    public Container() {
        items = new ArrayList<>();
    }

    public Container(Integer idCont, String name, Date lastSync, int type) {
        this();
        this.id = idCont;
        this.name = name;
        this.type = type;
        this.lastSync=lastSync;
    }

    public Container(Integer idCont, String name, Date lastSync, ArrayList<Item> items,
                     int type) {
        this.id = idCont;
        this.name = name;
        this.lastSync = lastSync;
        this.items = items;
        this.type = type;
    }

    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }

    public Integer getIdCont() {
        return id;
    }

    public void setIdCont(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
