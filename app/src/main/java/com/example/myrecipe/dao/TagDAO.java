package com.example.myrecipe.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecipe.models.Tag;

import java.util.List;
import java.util.List;

@Dao
public interface TagDAO {
    @Insert
    long insert(Tag recipe);

    @Delete
    void delete(Tag recipe);

    @Query("Select name from Tag")
    List<String> getAllTagNames();

    @Query("Select * from Tag")
    List<Tag> getAllTags();

    @Query("Select Tag.id, Tag.name " +
            "from Tag " +
            "inner join RecipeTag on Tag.id = RecipeTag.tagId " +
            "where RecipeTag.recipeId = :id;")
    List<Tag> getTagsByRecipeId(long id);
}
