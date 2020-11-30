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
    @PrimaryKey (autoGenerate = true)
    private long id;
    private long recipeId;
    @Ignore
    private String rawString;
    private String name;
    private double quantity;
    private String unitOfMeassure;

    @Ignore
    public Ingredient() {
        rawString = "";
        name = "";
        quantity = 0;
        unitOfMeassure = "";
    }

    public Ingredient(String name, double quantity, String unitOfMeassure) {
        this.rawString = name + " " + quantity;
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

    public String getRawString() {
        return rawString;
    }

    public void setRaw(String raw) {
        this.rawString = raw;
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

    public void setRawString(String rawString) {
        this.rawString = rawString;
    }

    public String getAsString(){
        DecimalFormat dec = new DecimalFormat("#0");
        return name+" "+dec.format(quantity)+ " " +unitOfMeassure;
    }
}
