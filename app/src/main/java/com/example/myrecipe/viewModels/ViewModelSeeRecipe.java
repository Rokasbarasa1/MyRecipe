package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.repository.GetRecipesRepository;
import com.example.myrecipe.repository.TagsRepository;

public class ViewModelSeeRecipe extends AndroidViewModel {
    private Recipe selectedRecipe;
    private GetRecipesRepository repo;

    public ViewModelSeeRecipe(@NonNull Application application) {
        super(application);
        repo = GetRecipesRepository.getInstance(application);
    }

    public void setSelectedRecipe(String recipeName){
        selectedRecipe = repo.getRecipeByName(recipeName);
    }

    public Recipe getRecipe(){
        return selectedRecipe;
    }
}
