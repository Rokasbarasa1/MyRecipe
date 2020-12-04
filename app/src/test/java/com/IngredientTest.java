package com;

import com.example.myrecipe.models.Ingredient;

import org.junit.Test;
import static org.junit.Assert.*;

public class IngredientTest {

     Ingredient ingredient;

    @Test
    public void createEmpty(){
        ingredient = new Ingredient();
    }

    @Test
    public void createFull(){
        ingredient = new Ingredient("Name", 0.1, "g");
    }

    //Empty constructor
    @Test
    public void emptyGetRawString(){
        ingredient = new Ingredient();
        assertEquals("", ingredient.getRawString());
    }

    @Test
    public void emptyGetName(){
        ingredient = new Ingredient();
        assertEquals("", ingredient.getName());
    }

    @Test
    public void emptyGetQuantity(){
        ingredient = new Ingredient();
        assertEquals(0, ingredient.getQuantity(), 0.1);
    }

    @Test
    public void emptyGetUnitOfMeasure(){
        ingredient = new Ingredient();
        assertEquals("", ingredient.getUnitOfMeassure());
    }

    //Full constructor
    @Test
    public void fullGetRawString(){
        ingredient = new Ingredient("Name", 0.1, "g");
        assertEquals("Name 0.1g", ingredient.getRawString());
    }

    @Test
    public void fullGetName(){
        ingredient = new Ingredient("Name", 0.1, "g");
        assertEquals("Name", ingredient.getName());
    }

    @Test
    public void fullGetQuantity(){
        ingredient = new Ingredient("Name", 0.1, "g");
        assertEquals(0.1, ingredient.getQuantity(), 0.1);
    }

    @Test
    public void fullGetUnitOfMeasure(){
        ingredient = new Ingredient("Name", 0.1, "g");
        assertEquals("g", ingredient.getUnitOfMeassure());
    }

    //Non constructor
    @Test
    public void setRaw(){
        ingredient = new Ingredient("Name", 0.1, "g");
        ingredient.setRaw("Raw");
        assertEquals("Raw", ingredient.getRawString());
    }
}