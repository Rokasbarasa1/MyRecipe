package com.example.myrecipe.models;

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
    public void emptyGetId(){
        ingredient = new Ingredient();
        assertEquals(0, ingredient.getId());
    }

    @Test
    public void emptyGetRecipeId(){
        ingredient = new Ingredient();
        assertEquals(0, ingredient.getRecipeId());
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

    @Test
    public void fullGetName(){
        ingredient = new Ingredient("Name", 0.1, "g");
        assertEquals("Name", ingredient.getName());
    }

    @Test
    public void emptyGetAsNameQuantityCombo(){
        ingredient = new Ingredient();
        assertEquals("", ingredient.getAsNameQuantityCombo());
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

    @Test
    public void GetUnitOfMeassure(){
        ingredient = new Ingredient("Name", 0.1, "g");
        assertEquals("Name 0.1", ingredient.getAsNameQuantityCombo());
    }
}