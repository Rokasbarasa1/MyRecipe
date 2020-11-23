package com.example.myrecipe.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.repository.TagsRepository;

import java.util.List;

public class ViewModelTagsExpanded extends ViewModel {
    private MutableLiveData<List<Recipe>> recipes;
    private TagsRepository repo;

    public void init(Tag expandedTag){
        if(recipes != null){
            return;
        }
        repo = TagsRepository.getInstance();
        recipes = repo.getRecipesByTag(expandedTag);
    }

    public MutableLiveData<List<Recipe>> getRecipesByTag(Tag expandedTag){
        return repo.getRecipesByTag(expandedTag);
    }

    public MutableLiveData<List<Recipe>> getRecipes(){
        return recipes;
    }

    public void refreshRecipes(Tag currentTag) {
        recipes = repo.getRecipesByTag(currentTag);
    }
}
