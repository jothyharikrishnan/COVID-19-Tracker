package com.example.covid19.model;

public class ItemModel {
    String field;
    String name;

    public ItemModel(String field, String name) {
        this.field = field;
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
