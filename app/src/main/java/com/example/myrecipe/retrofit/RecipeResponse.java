package com.example.myrecipe.retrofit;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeResponse {
    private List<RecipeResponse2> recipes;

    public class RecipeResponse2 {
        private List<IngredientResponse> extendedIngredients;
        private String title;
        private int readyInMinutes;
        private int servings;
        private String instructions;

        public Recipe getRecipe(){
            Recipe recipe = new Recipe(title, readyInMinutes, readyInMinutes, servings, instructions);

            List<Ingredient> ingredients = new ArrayList<>();
            for (int i = 0; i < extendedIngredients.size(); i++) {
                ingredients.add(new Ingredient(extendedIngredients.get(i).name, extendedIngredients.get(i).amount, extendedIngredients.get(i).unit));
            }
            recipe.setIngredients(ingredients);
            return recipe;
        }

        public class IngredientResponse{
            private String name;
            private Double amount;
            private String unit;

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
