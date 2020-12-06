package com.example.myrecipe.models;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

public class CalendarTodoTest {
    CalendarTodo calendarTodo;
    Calendar calendar;
    @Test
    public void TestCreateDatabaseConstructor(){
        calendar = Calendar.getInstance();
        calendarTodo = new CalendarTodo(1
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
                , calendar.get(Calendar.HOUR)
                , calendar.get(Calendar.MINUTE));
    }

    @Test
    public void TestCreateNormal(){
        calendar = Calendar.getInstance();
        calendarTodo = new CalendarTodo(1, (Calendar) calendar.clone());
    }

    @Before
    public void Before(){
        calendar = Calendar.getInstance();
        calendarTodo = new CalendarTodo(1, (Calendar) calendar.clone());
    }

    @Test
    public void TestGetId(){
        assertEquals(calendarTodo.getId(), 0);
    }

    @Test
    public void TestGetRecipeId(){
        assertEquals(calendarTodo.getRecipeId(), 1);
    }

    @Test
    public void TestGetYear(){
        assertEquals(calendarTodo.getYear(), calendar.get(Calendar.YEAR));
    }

    @Test
    public void TestGetMonth(){
        assertEquals(calendarTodo.getMonth(), calendar.get(Calendar.MONTH));
    }

    @Test
    public void TestGetDay(){
        assertEquals(calendarTodo.getDay(), calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void TestGetHour(){
        assertEquals(calendarTodo.getHour(), calendar.get(Calendar.HOUR));
    }

    @Test
    public void TestGetMinute(){
        assertEquals(calendarTodo.getMinute(), calendar.get(Calendar.MINUTE));
    }

    @Test
    public void TestGetCalendarTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
        System.out.println(dateFormat.format(calendarTodo.getCalendarTime().getTime()));
        System.out.println(dateFormat.format(calendar.getTime()) + "from the test class");
        assertEquals(dateFormat.format(calendarTodo.getCalendarTime().getTime()), dateFormat.format(calendar.getTime()));
    }

}