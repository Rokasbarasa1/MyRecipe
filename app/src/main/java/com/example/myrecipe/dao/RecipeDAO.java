package com.example.myrecipe.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecipe.models.Recipe;

import java.util.List;
import java.util.List;

@Dao
public interface RecipeDAO {
    @Insert
    long insert(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("select Recipe.id, Recipe.name, Recipe.prepTime, Recipe.cookTime, Recipe.servingSize, Recipe.description " +
            "from Recipe " +
            "join RecipeTag on RecipeTag.recipeId = Recipe.id " +
            "Where RecipeTag.tagId = :id;")
    List<Recipe> getRecipesByTagId(long id);

    @Query("Select * from recipe where name = :name;")
    Recipe getRecipeByName(String name);

    @Query("Select * from recipe;")
    List<Recipe> getAllRecipes();
}
