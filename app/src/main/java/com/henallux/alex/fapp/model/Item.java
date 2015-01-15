package com.henallux.alex.fapp.model;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Item {

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("name")
    private String name;
    @com.google.gson.annotations.SerializedName("expiryDate")
    private Date expiryDate;
    @com.google.gson.annotations.SerializedName("quantity")
    private Integer quantity;
    /*@com.google.gson.annotations.SerializedName("type")
    private Type type;*/
    @com.google.gson.annotations.SerializedName("lastSync")
    private Date lastSync;
    /*@com.google.gson.annotations.SerializedName("idAndroidContainer")
    private Integer idAndroidContainer;*/
    @com.google.gson.annotations.SerializedName("idAndroid")
    private Integer idAndroid;

    public Item() {
    }

    public Item(Integer idAndroid, String name, Date expiryDate, int quantity/*, Type type*/,
                Date lastSync/*, Integer idAndroidContainer*/) {
        this.idAndroid = idAndroid;
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        //this.type = type;
        this.lastSync = lastSync;
        //this.idAndroidContainer=idAndroidContainer;
    }

    public Integer getIdAndroid() {
        return idAndroid;
    }

    public void setIdAndroid(Integer idAndroid) {
        this.idAndroid = idAndroid;
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

    /*public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }*/

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

    /*public Integer getIdAndroidContainer() {return idAndroidContainer;  }

    public void setIdAndroidContainer(Integer idAndroidContainer) {this.idAndroidContainer = idAndroidContainer;    }*/
}
