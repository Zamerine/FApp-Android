package com.henallux.alex.fapp.azure;

import com.henallux.alex.fapp.model.Type;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alex on 15/01/2015.
 */
public class ItemAzure {

    @com.google.gson.annotations.SerializedName("id")
    private String azureId;
    @com.google.gson.annotations.SerializedName("name")
    private String name;
    @com.google.gson.annotations.SerializedName("expiryDate")
    private Date expiryDate;
    @com.google.gson.annotations.SerializedName("quantity")
    private int quantity;
    @com.google.gson.annotations.SerializedName("type")
    private String type;
    @com.google.gson.annotations.SerializedName("lastSync")
    private Date lastSync;

    public ItemAzure() {
    }

    public ItemAzure(String name, Date expiryDate, int quantity, String type,
                     Date lastSync) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.type = type;
        this.lastSync = lastSync;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getAzureId() {return azureId;}

    public void setAzureId(String azureId) {this.azureId = azureId;    }
}
