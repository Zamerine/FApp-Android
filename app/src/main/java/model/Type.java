package model;

/**
 * Created by Alex on 24/12/2014.
 */
public class Type {
    private int idType;
    private String name;
    private int freezerDuration;

    public Type() {
    }

    public Type(int idType, String name, int freezerDuration) {
        this.idType = idType;
        this.name = name;
        this.freezerDuration = freezerDuration;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFreezerDuration() {
        return freezerDuration;
    }

    public void setFreezerDuration(int freezerDuration) {
        this.freezerDuration = freezerDuration;
    }
}
