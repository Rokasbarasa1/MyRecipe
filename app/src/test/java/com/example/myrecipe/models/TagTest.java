package com.example.myrecipe.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TagTest {

    Tag tag;

    @Test
    public void TestCreate(){
        tag = new Tag("Name");
    }

    @Test
    public void TestCreateNoName(){
        try{
            tag = new Tag("");
            fail();
        }catch (IllegalArgumentException e){

        }
    }

    @Before
    public void SetUp(){
        tag = new Tag("Name");
    }

    @Test
    public void TestGetName(){
        assertEquals("Name", tag.getName());
    }

    @Test
    public void TestGetId(){
        assertEquals(0, tag.getId());
    }
}