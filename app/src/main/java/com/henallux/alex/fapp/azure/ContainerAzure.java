package com.henallux.alex.fapp.azure;

import com.henallux.alex.fapp.model.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alex on 15/01/2015.
 */
public class ContainerAzure {
    public static final int TYPE_FRIGO = 0;
    public static final int TYPE_FREEZER = 1;

    @com.google.gson.annotations.SerializedName("id")
    private String azureId;
    @com.google.gson.annotations.SerializedName("name")
    private String name;
    @com.google.gson.annotations.SerializedName("lastSync")
    private Date lastSync;
    @com.google.gson.annotations.SerializedName("type")
    private int type;

    public ContainerAzure() {

    }

    public ContainerAzure(String name, Date lastSync, int type) {
        this.name = name;
        this.type = type;
        this.lastSync=lastSync;
    }

    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
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

    public String getAzureId() {
        return azureId;
    }

    public void setAzureId(String azureId) {
        this.azureId = azureId;
    }
}
