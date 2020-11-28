package com.example.myrecipe.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.dao.IngredientDAO;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.dao.RecipeDAO;
import com.example.myrecipe.dao.RecipeDatabase;
import com.example.myrecipe.dao.RecipeTag;
import com.example.myrecipe.dao.RecipeTagDAO;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.dao.TagDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CreateRecipeRepository {
    private static CreateRecipeRepository instance;
    private RecipeDAO recipeDAO;
    private TagDAO tagDAO;
    private IngredientDAO ingredientDAO;
    private RecipeTagDAO recipeTagDAO;

    private CreateRecipeRepository(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDAO = database.recipeDAO();
        tagDAO = database.tagDAO();
        ingredientDAO = database.ingredientDAO();
        recipeTagDAO = database.recipeTagDAO();
    }

    public static CreateRecipeRepository getInstance(Application application){
        if(instance == null){
            instance = new CreateRecipeRepository(application);
        }
        return instance;
    }

    public void addRecipe(Recipe newRecipe) {
        try {
            List<String> currentTagStrings = new GetTagNamesAsync(tagDAO).execute().get();
            List<Tag> newTags = new ArrayList<>();
            List<Long> asociatedTagsIds = new ArrayList<>();
            for (int i = 0; i < newRecipe.getTags().size(); i++) {
                if(newRecipe.getTags().get(i).getName().equals(""))
                    continue;
                if(!currentTagStrings.contains(newRecipe.getTags().get(i).getName())){
                    newTags.add(newRecipe.getTags().get(i));
                }
            }
            for (int i = 0; i < newRecipe.getTags().size(); i++) {
                if(newRecipe.getTags().get(i).getId() != 0){
                    asociatedTagsIds.add(newRecipe.getTags().get(i).getId());
                }
            }
            insertRecipe(newRecipe, newTags, asociatedTagsIds);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insertRecipe(Recipe recipe, List<Tag> newTags, List<Long> asociatedTagsIds){
        try {
            long recipeId = new InsertRecipeAsync(recipeDAO).execute(recipe).get();
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.setRecipeId(recipeId);
                new InsertIngredientAsync(ingredientDAO).execute(ingredient);
            }
            if(newTags.size() != 0){
                for (Tag tag : newTags) {
                    asociatedTagsIds.add(new InsertTagAsync(tagDAO).execute(tag).get());
                }
            }
            if(asociatedTagsIds.size() != 0){
                for (long tagId : asociatedTagsIds) {
                    RecipeTag relationship = new RecipeTag(recipeId, tagId);
                    new InsertRecipeTagAsync(recipeTagDAO).execute(relationship);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Tag> getTags(){
        try {
            return new GetTagsAsync(tagDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetTagNamesAsync extends AsyncTask<Void, Void, List<String>>{
        private TagDAO tagDAO;

        private GetTagNamesAsync(TagDAO tagDAO){
            this.tagDAO = tagDAO;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return tagDAO.getAllTagNames();
        }
    }

    private class GetTagsAsync extends AsyncTask<Void, Void, List<Tag>>{
        private TagDAO tagDAO;

        private GetTagsAsync(TagDAO tagDAO){
            this.tagDAO = tagDAO;
        }

        @Override
        protected List<Tag> doInBackground(Void... voids) {
            return tagDAO.getAllTags();
        }
    }

    private class InsertRecipeAsync extends AsyncTask<Recipe, Void, Long> {
        private RecipeDAO recipeDAO;

        private InsertRecipeAsync(RecipeDAO recipeDAO){
            this.recipeDAO = recipeDAO;
        }

        @Override
        protected Long doInBackground(Recipe... recipes) {
            return recipeDAO.insert(recipes[0]);
        }
    }

    private class InsertIngredientAsync extends AsyncTask<Ingredient, Void, Void>{
        private IngredientDAO ingredientDAO;

        private InsertIngredientAsync(IngredientDAO ingredientDAO){
            this.ingredientDAO = ingredientDAO;
        }

        @Override
        protected Void doInBackground(Ingredient... ingredients) {
            ingredientDAO.insert(ingredients[0]);
            return null;
        }
    }

    private class InsertTagAsync extends AsyncTask<Tag, Void, Long>{
        private TagDAO tagDAO;

        private InsertTagAsync(TagDAO tagDAO){
            this.tagDAO = tagDAO;
        }

        @Override
        protected Long doInBackground(Tag... tags) {
            return tagDAO.insert(tags[0]);
        }
    }

    private class InsertRecipeTagAsync extends AsyncTask<RecipeTag, Void, Void>{
        private RecipeTagDAO recipeTagDAO;

        private InsertRecipeTagAsync(RecipeTagDAO recipeTagDAO){
            this.recipeTagDAO = recipeTagDAO;
        }

        @Override
        protected Void doInBackground(RecipeTag... recipeTags) {
            recipeTagDAO.insert(recipeTags[0]);
            return null;
        }
    }
}
