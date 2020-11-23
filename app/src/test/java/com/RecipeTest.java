package com;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class RecipeTest {

    private Recipe recipe;

    @Test
    public void TestCreateFull(){
        Ingredient ingredient1 = new Ingredient("1", 1, "g");
        Ingredient ingredient2 = new Ingredient("2", 2, "g");
        Ingredient ingredient3 = new Ingredient("3", 3, "g");
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient3);

        Tag tag1 = new Tag("Beef");
        Tag tag2 = new Tag("Chicken");
        Tag tag3 = new Tag("Lamb");
        ArrayList<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);

        recipe = new Recipe("Name", 1, 1, 1, ingredientList, "Description", tagList);
    }

    @Before
    public void SetUp(){
        Ingredient ingredient1 = new Ingredient("1", 1, "g");
        Ingredient ingredient2 = new Ingredient("2", 2, "g");
        Ingredient ingredient3 = new Ingredient("3", 3, "g");
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient3);

        Tag tag1 = new Tag("Beef");
        Tag tag2 = new Tag("Chicken");
        Tag tag3 = new Tag("Lamb");
        ArrayList<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);

        recipe = new Recipe("Name", 1, 1, 1, ingredientList, "Description", tagList);
    }

    //Empty constructor
    @Test
    public void TestGetName(){
        assertEquals("Name", recipe.getName());
    }

    @Test
    public void TestGetPrepTime(){
        assertEquals(1, recipe.getPrepTime());
    }

    @Test
    public void TestGetCookTime(){
        assertEquals(1, recipe.getCookTime());
    }

    @Test
    public void TestGetServingSize(){
        assertEquals(1, recipe.getServingSize());
    }

    @Test
    public void TestIngredients(){
        assertEquals(3, recipe.getIngredients().size());
        assertEquals("1 1.0g", recipe.getIngredients().get(0).getRaw());
        assertEquals("2 2.0g", recipe.getIngredients().get(1).getRaw());
        assertEquals("3 3.0g", recipe.getIngredients().get(2).getRaw());
    }

    @Test
    public void TestGetDescription(){
        assertEquals("Description", recipe.getDescription());
    }

    @Test
    public void TestTags(){
        assertEquals(3, recipe.getTags().size());
        assertEquals("Beef", recipe.getTags().get(0).getName());
        assertEquals("Chicken", recipe.getTags().get(1).getName());
        assertEquals("Lamb", recipe.getTags().get(2).getName());
    }

}