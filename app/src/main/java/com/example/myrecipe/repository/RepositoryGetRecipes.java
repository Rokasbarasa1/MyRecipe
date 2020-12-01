package com.example.myrecipe.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myrecipe.models.dao.CalendarTodoDAO;
import com.example.myrecipe.models.dao.GroceryTodoDAO;
import com.example.myrecipe.models.dao.IngredientDAO;
import com.example.myrecipe.models.CalendarTodo;
import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.dao.RecipeDAO;
import com.example.myrecipe.models.dao.RecipeDatabase;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.models.dao.TagDAO;


import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;



public class RepositoryGetRecipes {
    private static RepositoryGetRecipes instance;
    private RecipeDAO recipeDAO;
    private TagDAO tagDAO;
    private IngredientDAO ingredientDAO;
    private GroceryTodoDAO groceryTodoDAO;
    private CalendarTodoDAO calendarTodoDAO;
    private MutableLiveData<List<Recipe>> currentRecipes;

    private RepositoryGetRecipes(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDAO = database.recipeDAO();
        tagDAO = database.tagDAO();
        ingredientDAO = database.ingredientDAO();
        groceryTodoDAO = database.groceryTodoDAO();
        calendarTodoDAO = database.calendarTodoDAO();
        currentRecipes = new MutableLiveData<>();
    }

    public static RepositoryGetRecipes getInstance(Application application){
        if(instance == null){
            instance = new RepositoryGetRecipes(application);
        }
        return instance;
    }

    public LiveData<List<Recipe>> getRecipes(){
        return currentRecipes;
    }

    public void getRecipesByTag(Tag tag) {
        List<Recipe> selectedRecipes = null;
        try {
            selectedRecipes = new GetRecipesByTagAsync(recipeDAO, ingredientDAO, tagDAO).execute(tag).get();
            currentRecipes.setValue(selectedRecipes);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Recipe getRecipeByName(String recipeName) {
        Recipe selectedRecipe = null;
        try {
            selectedRecipe = new GetRecipeByNameAsync(recipeDAO, ingredientDAO, tagDAO).execute(recipeName).get();
            return selectedRecipe;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void newGroceryTodo(long recipeId, int servings, int ingredientAmount){
        //This is to know how much of every recipe is completed. How many ingredients checked that you got em.
        //For example there are 8 ingredients. The bit map will be 8 zeros long. When the user updates his
        //checked ingredients it updates the specific spots in the bit map using the index of checked ingredients.
        String bitMap = "";
        for (int i = 0; i < ingredientAmount; i++) {
            bitMap += "0";
        }
        GroceryTodo todo = new GroceryTodo(recipeId, servings, bitMap);
        new InsertGroceryTodoAsync(groceryTodoDAO).execute(todo);
    }

    public void newCalendarTodo(long recipeId, Calendar pointInTime) {
        CalendarTodo todo = new CalendarTodo(recipeId, pointInTime);
        new InsertCalendarTodoAsync(calendarTodoDAO).execute(todo);
    }

    public void newGroceryAndCalendarTodo(long recipeId, int servings, int ingredientAmount, Calendar pointInTime) {
        String bitMap = "";
        for (int i = 0; i < ingredientAmount; i++) {
            bitMap += "0";
        }
        GroceryTodo todoGrocery = new GroceryTodo(recipeId, servings, bitMap);
        CalendarTodo todoCalendar = new CalendarTodo(recipeId, pointInTime);
        new InsertGroceryTodoAsync(groceryTodoDAO).execute(todoGrocery);
        new InsertCalendarTodoAsync(calendarTodoDAO).execute(todoCalendar);
    }

    private class GetRecipesByTagAsync extends AsyncTask<Tag, Void, List<Recipe>> {
        private RecipeDAO recipeDAO;
        private IngredientDAO ingredientDAO;
        private TagDAO tagDAO;

        private GetRecipesByTagAsync(RecipeDAO recipeDAO, IngredientDAO ingredientDAO, TagDAO tagDAO){
            this.recipeDAO = recipeDAO;
            this.ingredientDAO = ingredientDAO;
            this.tagDAO = tagDAO;
        }

        @Override
        protected List<Recipe> doInBackground(Tag... tags) {
            List<Recipe> allRecipes = recipeDAO.getRecipesByTagId(tags[0].getId());
            for (int i = 0; i < allRecipes.size(); i++) {
                allRecipes.get(i).setTags(tagDAO.getTagsByRecipeId(allRecipes.get(i).getId()));
                allRecipes.get(i).setIngredients(ingredientDAO.getIngredientsByRecipeId(allRecipes.get(i).getId()));
            }
            return allRecipes;
        }
    }

    private class GetRecipeByNameAsync extends AsyncTask<String, Void, Recipe> {
        private RecipeDAO recipeDAO;
        private IngredientDAO ingredientDAO;
        private TagDAO tagDAO;


        private GetRecipeByNameAsync(RecipeDAO recipeDAO, IngredientDAO ingredientDAO, TagDAO tagDAO){
            this.recipeDAO = recipeDAO;
            this.ingredientDAO = ingredientDAO;
            this.tagDAO = tagDAO;
        }

        @Override
        protected Recipe doInBackground(String... strings) {
            Recipe recipe = recipeDAO.getRecipeByName(strings[0]);
            recipe.setTags(tagDAO.getTagsByRecipeId(recipe.getId()));
            recipe.setIngredients(ingredientDAO.getIngredientsByRecipeId(recipe.getId()));
            return recipe;
        }
    }

    private class InsertGroceryTodoAsync extends AsyncTask<GroceryTodo, Void, Long> {
        private GroceryTodoDAO groceryTodoDAO;

        private InsertGroceryTodoAsync(GroceryTodoDAO groceryTodoDAO){
            this.groceryTodoDAO = groceryTodoDAO;
        }

        @Override
        protected Long doInBackground(GroceryTodo... groceryTodos) {
            return groceryTodoDAO.insert(groceryTodos[0]);
        }
    }

    private class InsertCalendarTodoAsync extends AsyncTask<CalendarTodo, Void, Long> {
        private CalendarTodoDAO calendarTodoDAO;

        private InsertCalendarTodoAsync(CalendarTodoDAO calendarTodoDAO){
            this.calendarTodoDAO = calendarTodoDAO;
        }

        @Override
        protected Long doInBackground(CalendarTodo... calendarTodos) {
            return calendarTodoDAO.insert(calendarTodos[0]);
        }
    }
}
