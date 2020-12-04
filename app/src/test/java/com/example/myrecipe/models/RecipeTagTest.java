package com.example.myrecipe.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeTagTest {


    RecipeTag tag;

    @Test
    public void TestCreate(){
        tag = new RecipeTag(1, 1);
    }

    @Before
    public void before(){
        tag = new RecipeTag(1, 1);
    }

    @Test
    public void TestGetId(){
        assertEquals(tag.getId(), 0);
    }

    @Test
    public void TestGetRecipeId(){
        assertEquals(tag.getRecipeId(), 1);
    }

    @Test
    public void TestGetTagId(){
        assertEquals(tag.getTagId(), 1);
    }
}