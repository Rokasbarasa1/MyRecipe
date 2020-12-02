package com.example.myrecipe.models.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecipe.models.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    long insert(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    //Gets all recipes that have a relationship with a specific tag
    @Query("select Recipe.id, Recipe.name, Recipe.prepTime, Recipe.servingSize, Recipe.description " +
            "from Recipe " +
            "join RecipeTag on RecipeTag.recipeId = Recipe.id " +
            "Where RecipeTag.tagId = :id;")
    List<Recipe> getRecipesByTagId(long id);

    @Query("Select * from recipe where id = :recipeId;")
    Recipe getRecipeById(Long recipeId);

    @Query("Select * from recipe where name = :name;")
    Recipe getRecipeByName(String name);

    @Query("Select * from recipe;")
    List<Recipe> getAllRecipes();

    //Gets all recipes that have a relationship with any calendartodo
    @Query("select Recipe.id, Recipe.name, Recipe.prepTime, Recipe.servingSize, Recipe.description " +
            "from Recipe " +
            "join CalendarTodo on CalendarTodo.recipeId = Recipe.id ")
    List<Recipe> getMatchToSchedule();
}
