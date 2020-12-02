package com.example.myrecipe.models.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecipe.models.Tag;

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

    //Gets all tags that have a relationship with a specific recipe
    @Query("Select Tag.id, Tag.name " +
            "from Tag " +
            "inner join RecipeTag on Tag.id = RecipeTag.tagId " +
            "where RecipeTag.recipeId = :id;")
    List<Tag> getTagsByRecipeId(long id);


    @Query("delete from Tag where id in (select Tag.id " +
            "from Tag " +
            "EXCEPT " +
            "select Tag.id " +
            "from Tag " +
            "inner join RecipeTag on Tag.id == RecipeTag.tagId)")
    void deleteTagsThatDontHaveRelationships();
}
