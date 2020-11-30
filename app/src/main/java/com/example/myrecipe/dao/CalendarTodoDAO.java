package com.example.myrecipe.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.example.myrecipe.models.CalendarTodo;
import com.example.myrecipe.models.Recipe;

@Dao
public interface CalendarTodoDAO {
    @Insert
    long insert(CalendarTodo recipe);

    @Delete
    void delete(CalendarTodo recipe);
}
