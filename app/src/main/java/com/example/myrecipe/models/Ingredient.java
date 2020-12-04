package com.example.myrecipe.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.DecimalFormat;

@Entity(foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeId"),
        indices = {@Index(value = {"recipeId"})})
public class Ingredient {

    //Holds info for ingredients in the system that are associated to a recipe. I made these redundant in the database
    //meaning that there could be the same ingredients for multiple recipes but they are
    //separate entities in the database.

    @PrimaryKey (autoGenerate = true)
    long id;
    long recipeId;
    String name;
    double quantity;
    String unitOfMeassure;

    @Ignore
    public Ingredient() {
        name = "";
        quantity = 0;
        unitOfMeassure = "";
    }

    public Ingredient(String name, double quantity, String unitOfMeassure) {
        this.name = name;
        this.quantity = quantity;
        this.unitOfMeassure = unitOfMeassure;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public long getId() {
        return id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnitOfMeassure() {
        return unitOfMeassure;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsNameQuantityCombo(){
        if(quantity == 0){
            return name;
        }
        return name + " " + quantity;
    }

    public void setUnitOfMeassure(String unitOfMeassure) {
        this.unitOfMeassure = unitOfMeassure;
    }

    public String getAsString(){
        DecimalFormat dec = new DecimalFormat("#0.0");
        return name+" "+dec.format(quantity)+ " " +unitOfMeassure;
    }
}
