package com.example.myrecipe.models.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.models.Recipe;

import java.util.List;

@Dao
public interface GroceryTodoDAO {

    @Insert
    long insert(GroceryTodo groceryTodo);

    @Delete
    void delete(GroceryTodo groceryTodo);

    //Gets all recipes that have a grocerytodo assigned
    @Query("select Recipe.id, Recipe.name, Recipe.prepTime, Recipe.servingSize, Recipe.description " +
            "from Recipe " +
            "join GroceryTodo on GroceryTodo.recipeId = Recipe.id ")
    List<Recipe> getRecipesWithGroceryTodo();

    @Query("select * from grocerytodo")
    List<GroceryTodo> getGroceryTodos();

    @Update
    void update(GroceryTodo groceryTodo);

    @Query("delete from GroceryTodo where recipeId = :recipeId")
    void deleteAllGroceryTodoWithRecipeId(Long recipeId);
}
