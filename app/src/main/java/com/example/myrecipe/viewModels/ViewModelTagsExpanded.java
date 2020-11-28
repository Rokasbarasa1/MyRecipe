package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.repository.GetRecipesRepository;
import com.example.myrecipe.repository.TagsRepository;

import java.util.List;

public class ViewModelTagsExpanded extends AndroidViewModel {
    private MutableLiveData<List<Recipe>> recipes;
    private GetRecipesRepository repo;

    public ViewModelTagsExpanded(@NonNull Application application) {
        super(application);
        repo = GetRecipesRepository.getInstance(application);
    }

    public MutableLiveData<List<Recipe>> getRecipesByTag(Tag expandedTag){
        return repo.getRecipesByTag(expandedTag);
    }

    public void setExpandedTag(Tag expandedTag){
        recipes = repo.getRecipesByTag(expandedTag);
    }

    public MutableLiveData<List<Recipe>> getRecipes(){
        return recipes;
    }

    public void refreshRecipes(Tag currentTag) {
        recipes = repo.getRecipesByTag(currentTag);
    }
}
