package com;

import com.example.myrecipe.models.Tag;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TagTest {

    Tag tag;

    @Test
    public void TestCreate(){
        tag = new Tag("Name");
    }

    @Before
    public void SetUp(){
        tag = new Tag("Name");
    }

    @Test
    public void TestGetName(){
        assertEquals("Name", tag.getName());
    }
}