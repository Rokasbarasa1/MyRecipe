package com.example.myrecipe.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.myrecipe.dao.IngredientDAO;
import com.example.myrecipe.dao.RecipeTag;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.dao.RecipeDAO;
import com.example.myrecipe.dao.RecipeDatabase;
import com.example.myrecipe.dao.RecipeTagDAO;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.dao.TagDAO;

import java.util.List;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GetRecipesRepository {
    private static GetRecipesRepository instance;
    private RecipeDAO recipeDAO;
    private TagDAO tagDAO;
    private IngredientDAO ingredientDAO;
    private RecipeTagDAO recipeTagDAO;

    private GetRecipesRepository(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDAO = database.recipeDAO();
        tagDAO = database.tagDAO();
        ingredientDAO = database.ingredientDAO();
        recipeTagDAO = database.recipeTagDAO();
    }

    public static GetRecipesRepository getInstance(Application application){
        if(instance == null){
            instance = new GetRecipesRepository(application);
        }
        return instance;
    }

    public MutableLiveData<List<Recipe>> getRecipesByTag(Tag tag) {
        List<Recipe> selectedRecipes = null;
        try {
            selectedRecipes = new GetRecipesByTagAsync(recipeDAO, ingredientDAO, tagDAO, recipeTagDAO).execute(tag).get();
            MutableLiveData<List<Recipe>> data = new MutableLiveData<>();
            data.setValue(selectedRecipes);
            return data;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MutableLiveData<>();
    }

    public Recipe getRecipeByName(String recipeName) {
        Recipe selectedRecipe = null;
        try {
            selectedRecipe = new GetRecipeByNameAsync(recipeDAO).execute(recipeName).get();
            return selectedRecipe;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Recipe getRandomRecipe() {
        List<Recipe> recipes = null;
        try {
            recipes = new GetAllRecipesAsync(recipeDAO).execute().get();
            if(recipes.size() != 0){
                int randomNumber = 0 + (int)(Math.random() * recipes.size());
                return recipes.get(randomNumber);
            }else
                return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetRecipesByTagAsync extends AsyncTask<Tag, Void, List<Recipe>> {
        private RecipeDAO recipeDAO;
        private IngredientDAO ingredientDAO;
        private TagDAO tagDAO;
        private RecipeTagDAO recipeTagDAO;

        private GetRecipesByTagAsync(RecipeDAO recipeDAO, IngredientDAO ingredientDAO, TagDAO tagDAO, RecipeTagDAO recipeTagDAO){
            this.recipeDAO = recipeDAO;
            this.ingredientDAO = ingredientDAO;
            this.tagDAO = tagDAO;
        }

        @Override
        protected List<Recipe> doInBackground(Tag... tags) {
            List<Recipe> allRecipes = recipeDAO.getRecipesByTagId(tags[0].getId());
            for (int i = 0; i < allRecipes.size(); i++) {
                allRecipes.get(i).setTags(tagDAO.getTagsByRecipeId(allRecipes.get(i).getId()));
                allRecipes.get(i).setIngredients(ingredientDAO.getIngredientsByRecipeId(allRecipes.get(i).getId()));
            }
            return allRecipes;
        }
    }

    private class GetRecipeByNameAsync extends AsyncTask<String, Void, Recipe> {
        private RecipeDAO recipeDAO;

        private GetRecipeByNameAsync(RecipeDAO recipeDAO){
            this.recipeDAO = recipeDAO;
        }

        @Override
        protected Recipe doInBackground(String... strings) {
            Recipe recipe = recipeDAO.getRecipeByName(strings[0]);
            recipe.setTags(tagDAO.getTagsByRecipeId(recipe.getId()));
            recipe.setIngredients(ingredientDAO.getIngredientsByRecipeId(recipe.getId()));
            return recipe;
        }
    }

    private class GetAllRecipesAsync extends AsyncTask<Void, Void, List<Recipe>> {
        private RecipeDAO recipeDAO;

        private GetAllRecipesAsync(RecipeDAO recipeDAO){
            this.recipeDAO = recipeDAO;
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            List<Recipe> allRecipes = recipeDAO.getAllRecipes();
            for (int i = 0; i < allRecipes.size(); i++) {
                allRecipes.get(i).setTags(tagDAO.getTagsByRecipeId(allRecipes.get(i).getId()));
                allRecipes.get(i).setIngredients(ingredientDAO.getIngredientsByRecipeId(allRecipes.get(i).getId()));
            }
            return allRecipes;
        }
    }
}
