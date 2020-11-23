package com.example.myrecipe.models;

public class Tag {
    private String name;

    public Tag(String name){
        if(name.equals(""))
            throw new IllegalArgumentException();
        this.name = name;
    }

    public  String getName() {
        return name;
    }
}
