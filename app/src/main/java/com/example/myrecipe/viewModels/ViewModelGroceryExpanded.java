package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.repository.RepositoryGrocery;

public class ViewModelGroceryExpanded extends AndroidViewModel {
    RepositoryGrocery repo;

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
