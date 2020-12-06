package com.example.myrecipe.models;

import com.example.myrecipe.models.Recipe;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RecipeTest {

     Recipe recipe;

    @Test
    public void TestCreateFull(){
        Ingredient ingredient1 = new Ingredient("1", 1, "g");
        Ingredient ingredient2 = new Ingredient("2", 2, "g");
        Ingredient ingredient3 = new Ingredient("3", 3, "g");
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient3);

        Tag tag1 = new Tag("Beef");
        Tag tag2 = new Tag("Chicken");
        Tag tag3 = new Tag("Lamb");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);

        recipe = new Recipe("Name", 1, 1, ingredientList, "Description", tagList);
    }

    @Test
    public void TestCreateWithoutIngredients(){
        try{
            List<Ingredient> ingredientList = new ArrayList<>();
            Tag tag1 = new Tag("Beef");
            Tag tag2 = new Tag("Chicken");
            Tag tag3 = new Tag("Lamb");
            List<Tag> tagList = new ArrayList<>();
            tagList.add(tag1);
            tagList.add(tag2);
            tagList.add(tag3);

            recipe = new Recipe("Name", 1, 1, ingredientList, "Description", tagList);
            fail();
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void TestCreateWithoutName(){
        try{
            Ingredient ingredient1 = new Ingredient("1", 1, "g");
            Ingredient ingredient2 = new Ingredient("2", 2, "g");
            Ingredient ingredient3 = new Ingredient("3", 3, "g");
            List<Ingredient> ingredientList = new ArrayList<>();
            ingredientList.add(ingredient1);
            ingredientList.add(ingredient2);
            ingredientList.add(ingredient3);
            Tag tag1 = new Tag("Beef");
            Tag tag2 = new Tag("Chicken");
            Tag tag3 = new Tag("Lamb");
            List<Tag> tagList = new ArrayList<>();
            tagList.add(tag1);
            tagList.add(tag2);
            tagList.add(tag3);

            recipe = new Recipe("", 1, 1, ingredientList, "Description", tagList);
            fail();
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void TestCreateWithoutPrepTime(){
        try{
            Ingredient ingredient1 = new Ingredient("1", 1, "g");
            Ingredient ingredient2 = new Ingredient("2", 2, "g");
            Ingredient ingredient3 = new Ingredient("3", 3, "g");
            List<Ingredient> ingredientList = new ArrayList<>();
            ingredientList.add(ingredient1);
            ingredientList.add(ingredient2);
            ingredientList.add(ingredient3);
            Tag tag1 = new Tag("Beef");
            Tag tag2 = new Tag("Chicken");
            Tag tag3 = new Tag("Lamb");
            List<Tag> tagList = new ArrayList<>();
            tagList.add(tag1);
            tagList.add(tag2);
            tagList.add(tag3);

            recipe = new Recipe("Name", 0, 1, ingredientList, "Description", tagList);
            fail();
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void TestCreateWithoutServings(){
        try{
            Ingredient ingredient1 = new Ingredient("1", 1, "g");
            Ingredient ingredient2 = new Ingredient("2", 2, "g");
            Ingredient ingredient3 = new Ingredient("3", 3, "g");
            List<Ingredient> ingredientList = new ArrayList<>();
            ingredientList.add(ingredient1);
            ingredientList.add(ingredient2);
            ingredientList.add(ingredient3);
            Tag tag1 = new Tag("Beef");
            Tag tag2 = new Tag("Chicken");
            Tag tag3 = new Tag("Lamb");
            List<Tag> tagList = new ArrayList<>();
            tagList.add(tag1);
            tagList.add(tag2);
            tagList.add(tag3);

            recipe = new Recipe("Name", 1, 0, ingredientList, "Description", tagList);
            fail();
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void TestCreateWithoutDescription(){
        try{
            Ingredient ingredient1 = new Ingredient("1", 1, "g");
            Ingredient ingredient2 = new Ingredient("2", 2, "g");
            Ingredient ingredient3 = new Ingredient("3", 3, "g");
            List<Ingredient> ingredientList = new ArrayList<>();
            ingredientList.add(ingredient1);
            ingredientList.add(ingredient2);
            ingredientList.add(ingredient3);
            Tag tag1 = new Tag("Beef");
            Tag tag2 = new Tag("Chicken");
            Tag tag3 = new Tag("Lamb");
            List<Tag> tagList = new ArrayList<>();
            tagList.add(tag1);
            tagList.add(tag2);
            tagList.add(tag3);

            recipe = new Recipe("Name", 1, 1, ingredientList, "Description", tagList);
        }catch (IllegalArgumentException e){
            fail();
        }
    }

    @Test
    public void TestCreateWithoutTags(){
        try{
            Ingredient ingredient1 = new Ingredient("1", 1, "g");
            Ingredient ingredient2 = new Ingredient("2", 2, "g");
            Ingredient ingredient3 = new Ingredient("3", 3, "g");
            List<Ingredient> ingredientList = new ArrayList<>();
            ingredientList.add(ingredient1);
            ingredientList.add(ingredient2);
            ingredientList.add(ingredient3);

            List<Tag> tagList = new ArrayList<>();

            recipe = new Recipe("Name", 1, 1, ingredientList, "Description", tagList);
            fail();
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    public void TestCreateWithDatabaseConstructor(){
        recipe = new Recipe("Name", 1, 1, "Description");
    }

    @Before
    public void SetUp(){
        Ingredient ingredient1 = new Ingredient("1", 1, "g");
        Ingredient ingredient2 = new Ingredient("2", 2, "g");
        Ingredient ingredient3 = new Ingredient("3", 3, "g");
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient3);

        Tag tag1 = new Tag("Beef");
        Tag tag2 = new Tag("Chicken");
        Tag tag3 = new Tag("Lamb");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);

        recipe = new Recipe("Name", 1, 1, ingredientList, "Description", tagList);
    }

    @Test
    public void TestGetId(){
        assertEquals(0, recipe.getId());
    }

    @Test
    public void TestGetName(){
        assertEquals("Name", recipe.getName());
    }

    @Test
    public void TestGetPrepTime(){
        assertEquals(1, recipe.getPrepTime());
    }

    @Test
    public void TestGetServingSize(){
        assertEquals(1, recipe.getServingSize());
    }

    @Test
    public void TestIngredients(){
        assertEquals(3, recipe.getIngredients().size());
        assertEquals("1 1.0", recipe.getIngredients().get(0).getAsNameQuantityCombo());
        assertEquals("2 2.0", recipe.getIngredients().get(1).getAsNameQuantityCombo());
        assertEquals("3 3.0", recipe.getIngredients().get(2).getAsNameQuantityCombo());
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