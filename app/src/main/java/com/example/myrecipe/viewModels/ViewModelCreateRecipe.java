package com.example.myrecipe.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.repository.CreateRecipeRepository;
import com.example.myrecipe.repository.TagsRepository;

import java.util.ArrayList;
import java.util.List;


public class ViewModelCreateRecipe extends AndroidViewModel {
    private CreateRecipeRepository repo;
    private List<Ingredient> ingredients;

    public ViewModelCreateRecipe(@NonNull Application application) {
        super(application);
        repo = CreateRecipeRepository.getInstance(application);
        ingredients = new ArrayList<>();
    }

    public void addRecipe(String name, String prepTime, String cookTime, String servingSize, String description, String tags){
        List<Tag> splitTags = new ArrayList<>();
        List<Tag> tagsInSystem = repo.getTags();
        String[] tagsIndividual = tags.split(",");
        boolean added;
        for (int i = 0; i < tagsIndividual.length; i++) {
            added = false;
            for (int j = 0; j < tagsInSystem.size(); j++) {
                if(tagsIndividual[i].equals(tagsInSystem.get(j).getName())){
                    splitTags.add(tagsInSystem.get(j));
                    added = true;
                    break;
                }
            }
            if(!added)
                splitTags.add(new Tag(tagsIndividual[i]));
        }
        Recipe newRecipe = new Recipe(name, Integer.parseInt(prepTime), Integer.parseInt(cookTime), Integer.parseInt(servingSize), ingredients, description, splitTags);
        repo.addRecipe(newRecipe);
    }

    public void ingredientUpdated(int index, String text) {
        if(!text.equals("")){
            String[] ingredient = text.split(" ");
            int amount = 0;
            int number = 0;
            boolean exists = false;
            for (int i = 0; i < ingredient.length; i++) {
                try{
                    amount = Integer.parseInt(ingredient[i]);
                    number = i;
                }catch(NumberFormatException e){
                }
            }
            text = text.substring(0, text.length() - ingredient[number].length());
            Ingredient newIngredient = new Ingredient(text, amount, "Gram");
            try{
                ingredients.get(index);
                exists = true;
            }catch (IndexOutOfBoundsException e){
            }
            if(exists){
                ingredients.set(index, newIngredient);
            }else {
                ingredients.add(newIngredient);
            }
        }
    }
}
