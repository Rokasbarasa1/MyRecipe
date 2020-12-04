package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.repository.RepositoryRandomRecipe;

public class ViewModelRandom extends AndroidViewModel {
    RepositoryRandomRecipe repo;

    public ViewModelRandom(@NonNull Application application) {
        super(application);
        repo = RepositoryRandomRecipe.getInstance(application);
    }

    public LiveData<Recipe> getRecipe(){
        return repo.getRecipe();
    }

    public void getRandomRecipe() {
        repo.getRandomRecipe();
    }

    public void getRandomRecipeFromInternet() {
        repo.getRandomRecipeFromInternet();
    }
}
