package com.example.myrecipe.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GroceryTodoTest {
    GroceryTodo groceryTodo;

    @Test
    public void TestCreate(){
        groceryTodo = new GroceryTodo(1,1,"000000");
    }

    @Before
    public void Before(){
        groceryTodo = new GroceryTodo(1,1,"000000");
    }

    @Test
    public void TestGetRecipeId(){
        assertEquals(groceryTodo.getRecipeId(), 1);
    }

    @Test
    public void TestGetId(){
        assertEquals(groceryTodo.getServingSize(), 1);
    }

    @Test
    public void TestGetBitMap(){
        assertEquals(groceryTodo.getStatusBitMap(), "000000");
    }
}