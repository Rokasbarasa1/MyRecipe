package com.example.myrecipe.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.repository.TagsRepository;

public class ViewModelSeeRecipe extends ViewModel {
    private Recipe selectedRecipe;
    private TagsRepository repo;

    public void init(String recipeName){
        repo = TagsRepository.getInstance();
        selectedRecipe = repo.getRecipeByName(recipeName);
    }

    public Recipe getRecipe(){
        return selectedRecipe;
    }
}
