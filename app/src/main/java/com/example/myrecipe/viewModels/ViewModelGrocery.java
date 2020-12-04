package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.repository.RepositoryGrocery;

import java.util.List;

public class ViewModelGrocery extends AndroidViewModel {
    RepositoryGrocery repo;

    public ViewModelGrocery(@NonNull Application application) {
        super(application);
        repo = RepositoryGrocery.getInstance(application);

    }

    public LiveData<List<Recipe>> getRecipeGroceries() {
        return repo.getRecipeGroceries();
    }

    public void getRecipesForList() {
        repo.getRecipeGroceriesUpdate();
    }

    public List<GroceryTodo> getGroceryTodos() {
        return repo.getGroceryTodos();
    }
}

