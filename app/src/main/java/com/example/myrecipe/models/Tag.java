package com.example.myrecipe.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tag {
    @PrimaryKey (autoGenerate = true)
    private long id;
    private String name;

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
