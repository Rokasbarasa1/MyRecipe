package com.example.myrecipe.models.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecipe.models.CalendarTodo;

import java.util.List;

@Dao
public interface CalendarTodoDAO {

    @Insert
    long insert(CalendarTodo recipe);

    @Delete
    void delete(CalendarTodo recipe);

    @Query( "Select * " +
            "from CalendarTodo " +
            "order by year, month, day, hour, minute")
    List<CalendarTodo> getAllSchedules();
}
