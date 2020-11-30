package com.example.myrecipe.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeId"),
        indices = {@Index(value = {"recipeId"})})
public class GroceryTodo {
    @PrimaryKey (autoGenerate = true)
    private long id;
    private long recipeId;
    private int servingSize;
    private String statusBitMap;

    public GroceryTodo(long recipeId, int servingSize, String statusBitMap) {
        this.recipeId = recipeId;
        this.servingSize = servingSize;
        this.statusBitMap = statusBitMap;
    }

    public long getId() {
        return id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public int getServingSize() {
        return servingSize;
    }

    public String getStatusBitMap() {
        return statusBitMap;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setStatusBitMap(String statusBitMap) {
        this.statusBitMap = statusBitMap;
    }
}
