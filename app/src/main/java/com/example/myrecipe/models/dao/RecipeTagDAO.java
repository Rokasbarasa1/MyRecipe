package com.example.myrecipe.models.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecipe.models.RecipeTag;

@Dao
public interface RecipeTagDAO {

    @Insert
    void insert(RecipeTag recipe);

    @Delete
    void delete(RecipeTag recipe);

    @Query("Delete from RecipeTag Where  recipeId = :recipeId")
    void deleteAllRecipeTagDaoWithRecipeId(Long recipeId);
}
