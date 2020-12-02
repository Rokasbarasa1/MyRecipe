package com.example.myrecipe.models.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecipe.models.Ingredient;

import java.util.List;

@Dao
public interface IngredientDAO {

    @Insert
    void insert(Ingredient recipe);

    @Delete
    void delete(Ingredient recipe);

    //Gets all ingredients for a specific recipe
    @Query("Select * from ingredient Where recipeId = :idOfRecipe")
    List<Ingredient> getIngredientsByRecipeId(long idOfRecipe);

    @Query("delete from Ingredient where recipeId = :recipeId")
    void deleteAllIngredientsWithRecipeId(Long recipeId);
}
