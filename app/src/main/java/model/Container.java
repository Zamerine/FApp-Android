package model;

import java.util.GregorianCalendar;

/**
 * Created by Alex on 24/12/2014.
 */
public class Container {
    private int idCont;
    private String name;
    private GregorianCalendar lastSync;


    private String type;

    public Container() {
    }

    public Container(int idCont, String name, GregorianCalendar lastSync, String type) {
        this.idCont = idCont;
        this.name = name;
        this.type = type;
        this.lastSync=lastSync;
    }

    public GregorianCalendar getLastSync() {
        return lastSync;
    }

    public void setLastSync(GregorianCalendar lastSync) {
        this.lastSync = lastSync;
    }

    public int getIdCont() {
        return idCont;
    }

    public void setIdCont(int idCont) {
        this.idCont = idCont;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
