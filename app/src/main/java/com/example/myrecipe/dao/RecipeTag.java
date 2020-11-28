package com.example.myrecipe.dao;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;

@Entity(foreignKeys = {
        @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeId"),
        @ForeignKey(entity = Tag.class, parentColumns = "id", childColumns = "tagId")
})
public class RecipeTag {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long recipeId;
    private long tagId;

    public RecipeTag(long recipeId, long tagId){
        this.recipeId = recipeId;
        this.tagId = tagId;
    }

    public long getId() {
        return id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setId(long id) {
        this.id = id;
    }
}
