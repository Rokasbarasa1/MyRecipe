package com.example.myrecipe.models;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private int prepTime;
    private int cookTime;
    private int servingSize;
    private ArrayList<Ingredient> ingredients;
    private String description;
    private ArrayList<Tag> tags;

    public Recipe(String name, int prepTime, int cookTime, int servingSize, ArrayList<Ingredient> ingredients, String description, ArrayList<Tag> tags) {
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

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Tag> getTags() {
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

    public void setDescription(String description) {
        this.description = description;
    }
}
