package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.repository.GetRecipesRepository;
import com.example.myrecipe.repository.TagsRepository;

public class ViewModelRandom extends AndroidViewModel {
    private GetRecipesRepository repo;

    public ViewModelRandom(@NonNull Application application) {
        super(application);
        repo = GetRecipesRepository.getInstance(application);
    }

    public Recipe getRandomRecipe() {
        return repo.getRandomRecipe();
    }
}
