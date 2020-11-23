package com.example.myrecipe.viewModels;

import androidx.lifecycle.ViewModel;

import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.repository.TagsRepository;

public class ViewModelRandom extends ViewModel {
    private TagsRepository repo;

    public void init(){
        repo = TagsRepository.getInstance();
    }

    public Recipe getRandomRecipe() {
        return repo.getRandomRecipe();
    }
}
