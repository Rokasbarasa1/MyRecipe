package com.example.myrecipe.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tag {

    //Holds tags, pretty simple. Is a way of navigating recipes. Recipes are supposed to belong under
    //multiple tags. Like a recipe can be in asian and in rice section and many more for example.
    //Its still the same recipe just has multiple angles to it.

    @PrimaryKey (autoGenerate = true)
    long id;
    String name;

    public Tag(String name){
        if(name.equals(""))
            throw new IllegalArgumentException();
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public  String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }
}
