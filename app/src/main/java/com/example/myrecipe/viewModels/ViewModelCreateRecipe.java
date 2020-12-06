package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.repository.RepositoryCreateRecipe;

import java.util.ArrayList;
import java.util.List;


public class ViewModelCreateRecipe extends AndroidViewModel {

    RepositoryCreateRecipe repo;

    public ViewModelCreateRecipe(@NonNull Application application) {
        super(application);
        repo = RepositoryCreateRecipe.getInstance(application);
    }

    //Finds the tags user entered. Looks at what the system already has. If a tag matches it uses the system tag instead.
    //Assigns these tags to the new recipe along with ingredients that it collected earlier.
    public void addRecipe(String name, String prepTime, String servingSize, List<Ingredient> ingredients, String description, String tags){
        List<Tag> splitTags = new ArrayList<>();
        List<Tag> tagsInSystem = repo.getTags();
        String[] tagsIndividual = tags.split(",");
        boolean added;
        for (String s : tagsIndividual) {
            added = false;
            for (int j = 0; j < tagsInSystem.size(); j++) {
                if (s.equals(tagsInSystem.get(j).getName())) {
                    splitTags.add(tagsInSystem.get(j));
                    added = true;
                    break;
                }
            }
            if (!added)
                splitTags.add(new Tag(s));
        }
        Recipe newRecipe = new Recipe(name, Integer.parseInt(prepTime), Integer.parseInt(servingSize), ingredients, description, splitTags);
        repo.addRecipe(newRecipe);
    }



}
