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
    List<Ingredient> ingredients;

    public ViewModelCreateRecipe(@NonNull Application application) {
        super(application);
        repo = RepositoryCreateRecipe.getInstance(application);
        ingredients = new ArrayList<>();
    }

    //Finds the tags user entered. Looks at what the system already has. If a tag matches it uses the system tag instead.
    //Assigns these tags to the new recipe along with ingredients that it collected earlier.
    public void addRecipe(String name, String prepTime, String servingSize, String description, String tags){
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

    //Runs every time a use makes a change. Pretty beefy, but was the only way i could save the state of what
    //the user entered before removing a ingredient.
    //Divides
    public void ingredientUpdated(int index, String text, String textUnits) {
        if(!text.matches("")){
            String[] ingredient = text.split(" ");
            double amount = 0;
            int number = 0;
            for (int i = 0; i < ingredient.length; i++) {
                try{
                    amount = Double.parseDouble(ingredient[i]);
                    number = i;

                }catch(NumberFormatException ignored){
                }
            }
            text = text.substring(0, text.length() - ingredient[number].length());
            Ingredient newIngredient = new Ingredient(text, amount, textUnits);
            System.out.println("recipe created");
            try{
                ingredients.get(index);
                ingredients.set(index, newIngredient);
            }catch (IndexOutOfBoundsException e){
                ingredients.add(newIngredient);
            }
            System.out.println(text);
            System.out.println(amount);
        }
    }

    public List<Ingredient> getIngredientsUpdated() {
        return ingredients;
    }

    public void removeLastLine(int i) {
        try{
            ingredients.remove(i);
        } catch (Exception e){

        }
    }
}
