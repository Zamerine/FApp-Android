package model;

import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Item {
    private int idItem;
    private String name;
    private GregorianCalendar expiryDate;
    private int quantity;
    private Type type;
    private Container container;

    public Item() {
    }

    public Item(int idItem, String name, GregorianCalendar expiryDate, int quantity, Type type, Container container) {
        this.idItem = idItem;
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.type = type;
        this.container = container;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
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

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public GregorianCalendar getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(GregorianCalendar expiryDate) {
        this.expiryDate = expiryDate;
    }
}
