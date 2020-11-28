package com.example.myrecipe.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
import java.util.List;

@Entity
public class Recipe {
    @PrimaryKey (autoGenerate = true)
    private long id;
    private String name;
    private int prepTime;
    private int cookTime;
    private int servingSize;
    @Ignore
    private List<Ingredient> ingredients;
    private String description;
    @Ignore
    private List<Tag> tags;

    public Recipe(String name, int prepTime, int cookTime, int servingSize, String description){
        this.name = name;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servingSize = servingSize;
        this.description = description;
    }
    @Ignore
    public Recipe(String name, int prepTime, int cookTime, int servingSize, List<Ingredient> ingredients, String description, List<Tag> tags) {
        if(name.equals("") || prepTime < 1 || cookTime < 1 || servingSize < 1|| ingredients.size() == 0 || tags.size() == 0)
            throw new IllegalArgumentException();
        this.name = name;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
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

    public int getCookTime() {
        return cookTime;
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

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
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
