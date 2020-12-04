package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.repository.RepositoryGetRecipes;

import java.util.Calendar;

public class ViewModelSeeRecipe extends AndroidViewModel {
    Recipe selectedRecipe;
    RepositoryGetRecipes repo;

    public ViewModelSeeRecipe(@NonNull Application application) {
        super(application);
        repo = RepositoryGetRecipes.getInstance(application);
    }

    public void setSelectedRecipe(long recipeId){
        selectedRecipe = repo.getRecipeByName(recipeId);
    }

    public Recipe getRecipe(){
        return selectedRecipe;
    }

    public void newGroceryTodo(long recipeId, int servings, int ingredientAmount) {
        repo.newGroceryTodo(recipeId, servings, ingredientAmount);
    }

    public void newCalendarTodo(long recipeId, Calendar pointInTime) {
        repo.newCalendarTodo(recipeId, pointInTime);
    }

    public void newGroceryAndCalendarTodo(long recipeId, int servings, int ingredientAmount, Calendar pointInTime) {
        repo.newGroceryAndCalendarTodo(recipeId, servings, ingredientAmount, pointInTime);
    }

    public void onDeleteRecipe(long id) {
        repo.deleteRecipe(id);
    }
}
