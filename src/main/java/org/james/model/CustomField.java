package org.james.model;

public class CustomField {

    private int id;
    private String value;

    public CustomField(){

    }

    public CustomField(int id, String value){
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
