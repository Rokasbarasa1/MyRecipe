package com.example.myrecipe.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myrecipe.models.dao.CalendarTodoDAO;
import com.example.myrecipe.models.dao.GroceryTodoDAO;
import com.example.myrecipe.models.dao.IngredientDAO;
import com.example.myrecipe.models.dao.RecipeDAO;
import com.example.myrecipe.models.dao.RecipeDatabase;
import com.example.myrecipe.models.dao.TagDAO;
import com.example.myrecipe.models.CalendarTodo;
import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.models.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RepositoryGrocery {
    private static RepositoryGrocery instance;
    private RecipeDAO recipeDAO;
    private TagDAO tagDAO;
    private IngredientDAO ingredientDAO;
    private GroceryTodoDAO groceryTodoDAO;
    private CalendarTodoDAO calendarTodoDAO;
    private MutableLiveData<List<Recipe>> groceryRecipes;

    private RepositoryGrocery(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDAO = database.recipeDAO();
        tagDAO = database.tagDAO();
        ingredientDAO = database.ingredientDAO();
        groceryTodoDAO = database.groceryTodoDAO();
        calendarTodoDAO = database.calendarTodoDAO();
        groceryRecipes = new MutableLiveData<>();
    }

    public static RepositoryGrocery getInstance(Application application){
        if(instance == null){
            instance = new RepositoryGrocery(application);
        }
        return instance;
    }

    public LiveData<List<Recipe>> getRecipeGroceries() {
        return groceryRecipes;
    }

    public void getRecipeGroceriesUpdate() {
        try {
            List<Recipe> recipes = new GetGroceryRecipesAsync(groceryTodoDAO, ingredientDAO).execute().get();
            groceryRecipes.setValue(recipes);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<GroceryTodo> getGroceryTodos() {
        List<GroceryTodo> todos = new ArrayList<>();
        try{
            todos = new GetGroceryTodosAsync(groceryTodoDAO).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public void saveChangesToStatus(GroceryTodo groceryTodo) {
        new SaveChangesToStatusAsync(groceryTodoDAO).execute(groceryTodo);
    }

    public void finishGroceryList(GroceryTodo groceryTodo) {
        new DeleteGroceryTodoAsync(groceryTodoDAO).execute(groceryTodo);
    }

    private class GetGroceryRecipesAsync extends AsyncTask<CalendarTodo, Void, List<Recipe>> {
        private GroceryTodoDAO groceryTodoDAO;
        private IngredientDAO ingredientDAO;

        private GetGroceryRecipesAsync(GroceryTodoDAO groceryTodoDAO, IngredientDAO ingredientDAO){
            this.groceryTodoDAO = groceryTodoDAO;
            this.ingredientDAO = ingredientDAO;
        }

        @Override
        protected List<Recipe> doInBackground(CalendarTodo... calendarTodos) {
            List<Recipe> recipes = groceryTodoDAO.getRecipesWithGroceryTodo();
            for (int i = 0; i < recipes.size(); i++) {
                recipes.get(i).setIngredients(ingredientDAO.getIngredientsByRecipeId(recipes.get(i).getId()));
            }
            return recipes;
        }
    }

    private class GetGroceryTodosAsync extends AsyncTask<CalendarTodo, Void, List<GroceryTodo>> {
        private GroceryTodoDAO groceryTodoDAO;

        private GetGroceryTodosAsync(GroceryTodoDAO groceryTodoDAO){
            this.groceryTodoDAO = groceryTodoDAO;
        }

        @Override
        protected List<GroceryTodo> doInBackground(CalendarTodo... calendarTodos) {
            return groceryTodoDAO.getGroceryTodos();
        }
    }

    private class SaveChangesToStatusAsync extends AsyncTask<GroceryTodo, Void, Void> {
        private GroceryTodoDAO groceryTodoDAO;

        private SaveChangesToStatusAsync(GroceryTodoDAO groceryTodoDAO){
            this.groceryTodoDAO = groceryTodoDAO;
        }

        @Override
        protected Void doInBackground(GroceryTodo... groceryTodos) {
            groceryTodoDAO.update(groceryTodos[0]);
            return null;
        }
    }

    private class DeleteGroceryTodoAsync extends AsyncTask<GroceryTodo, Void, Void> {
        private GroceryTodoDAO groceryTodoDAO;

        private DeleteGroceryTodoAsync(GroceryTodoDAO groceryTodoDAO){
            this.groceryTodoDAO = groceryTodoDAO;
        }

        @Override
        protected Void doInBackground(GroceryTodo... groceryTodos) {
            groceryTodoDAO.delete(groceryTodos[0]);
            return null;
        }
    }
}
