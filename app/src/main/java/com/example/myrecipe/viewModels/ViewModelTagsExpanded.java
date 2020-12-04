package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.repository.RepositoryGetRecipes;

import java.util.List;

public class ViewModelTagsExpanded extends AndroidViewModel {
    RepositoryGetRecipes repo;

    public ViewModelTagsExpanded(@NonNull Application application) {
        super(application);
        repo = RepositoryGetRecipes.getInstance(application);
    }

    public void getRecipesByTag(Tag expandedTag){
        repo.getRecipesByTag(expandedTag);
    }

    public LiveData<List<Recipe>> getRecipes(){
        return repo.getRecipes();
    }
}
