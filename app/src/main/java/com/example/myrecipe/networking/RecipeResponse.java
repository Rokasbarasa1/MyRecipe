package com.example.myrecipe.networking;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeResponse {

    //Api returns a "list" with a single item in it...
    List<RecipeResponse2> recipes;

    public class RecipeResponse2 {
         List<IngredientResponse> extendedIngredients;
         String title;
         int readyInMinutes;
         int servings;
         String instructions;

        public Recipe getRecipe(){
            Recipe recipe = new Recipe(title, readyInMinutes, servings, instructions);

            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 0; i < extendedIngredients.size(); i++) {
                ingredients.add(new Ingredient(extendedIngredients.get(i).getName(), extendedIngredients.get(i).getAmount(), extendedIngredients.get(i).getUnit()));
            }
            recipe.setIngredients(ingredients);
            return recipe;
        }

        public class IngredientResponse{
             String name;
             Double amount;
             String unit;

            public String getName() {
                return name;
            }

            public Double getAmount() {
                return amount;
            }

            public String getUnit() {
                return unit;
            }
        }
    }

    public RecipeResponse2 getRecipe(){
        return recipes.get(0);
    }
}
