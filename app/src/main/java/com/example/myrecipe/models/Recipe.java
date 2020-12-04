package com.example.myrecipe.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Recipe {

    //Is the main aspect of the system and the main thing this app manages. Holds info on recipe

    @PrimaryKey (autoGenerate = true)
    long id;
    String name;
    int prepTime;
    int servingSize;
    @Ignore
    List<Ingredient> ingredients;
    String description;
    @Ignore
    List<Tag> tags;

    @Ignore
    public Recipe(String name){
        this.name = name;
    }

    public Recipe(String name, int prepTime, int servingSize, String description){
        this.name = name;
        this.prepTime = prepTime;
        this.servingSize = servingSize;
        this.description = description;
    }
    @Ignore
    public Recipe(String name, int prepTime, int servingSize, List<Ingredient> ingredients, String description, List<Tag> tags) {
        if(name.matches("") || prepTime < 1 || servingSize < 1|| ingredients.size() == 0 || tags.size() == 0)
            throw new IllegalArgumentException();
        this.name = name;
        this.prepTime = prepTime;
        this.servingSize = servingSize;
        this.ingredients = ingredients;
        this.description = description;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getName() {
        return name;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public int getServingSize() {
        return servingSize;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
