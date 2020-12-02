package com.example.myrecipe.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(foreignKeys = @ForeignKey(entity = Recipe.class, parentColumns = "id", childColumns = "recipeId"),
        indices = {@Index(value = {"recipeId"})})
public class CalendarTodo {

    //Holds information about a calendar/schedule event tied to a recipe.

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long recipeId;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    @Ignore
    private Calendar calendarTime;

    public CalendarTodo(long recipeId, int year, int month, int day, int hour, int minute) {
        this.recipeId = recipeId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        calendarTime = Calendar.getInstance();
        calendarTime.set(this.year, this.month, this.day, this.hour, this.minute);
    }

    @Ignore
    public CalendarTodo(long recipeId, Calendar pointInTime) {
        this.recipeId = recipeId;
        this.year = pointInTime.get(Calendar.YEAR);
        this.month = pointInTime.get(Calendar.MONTH);
        this.day = pointInTime.get(Calendar.DAY_OF_MONTH);
        this.hour = pointInTime.get(Calendar.HOUR_OF_DAY);
        this.minute = pointInTime.get(Calendar.MINUTE);
    }

    public long getId() {
        return id;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Calendar getCalendarTime() {
        return calendarTime;
    }

}
