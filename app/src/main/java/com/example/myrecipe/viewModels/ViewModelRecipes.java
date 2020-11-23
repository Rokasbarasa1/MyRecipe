package com.example.myrecipe.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe.models.Recipe;

import java.util.List;

public class ViewModelRecipes extends ViewModel {
    private MutableLiveData<List<Recipe>> recipes;
}
