package com.example.myrecipe.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

@Dao
public interface RecipeTagDAO {
    @Insert
    void insert(RecipeTag recipe);

    @Delete
    void delete(RecipeTag recipe);


}
