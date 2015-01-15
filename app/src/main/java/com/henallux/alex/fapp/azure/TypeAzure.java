package com.henallux.alex.fapp.azure;

/**
 * Created by Alex on 15/01/2015.
 */
public class TypeAzure {private int id;
    @com.google.gson.annotations.SerializedName("id")
    private String azureId;
    @com.google.gson.annotations.SerializedName("name")
    private String name;
    @com.google.gson.annotations.SerializedName("freezerDuration")
    private Integer freezerDuration;
    @com.google.gson.annotations.SerializedName("defaultExpiryDate")
    private Integer defaultExpiryDate;

    public TypeAzure() {
    }

    public TypeAzure(int id, String name, Integer freezerDuration, Integer defaultExpiryDate) {
        this.id = id;
        this.name = name;
        this.freezerDuration = freezerDuration;
        this.defaultExpiryDate = defaultExpiryDate;
    }

    public TypeAzure(int id, String name, Integer freezerDuration) {
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

    public String getAzureId() {
        return azureId;
    }

    public void setAzureId(String azureId) {
        this.azureId = azureId;
    }

    @Override
    public String toString() {
        return name;
    }
}