package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.repository.RepositoryGrocery;

import java.util.List;

public class ViewModelGroceryExpanded extends AndroidViewModel {
    private RepositoryGrocery repo;

    public ViewModelGroceryExpanded(@NonNull Application application) {
        super(application);
        repo = RepositoryGrocery.getInstance(application);

    }

    public void saveChangesToStatus(GroceryTodo groceryTodo) {
        repo.saveChangesToStatus(groceryTodo);
    }

    public void finishGroceryList(GroceryTodo groceryTodo) {
        repo.finishGroceryList(groceryTodo);
    }
}
