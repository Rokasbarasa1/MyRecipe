package com.example.myrecipe.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.dao.IngredientDAO;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.dao.RecipeDAO;
import com.example.myrecipe.models.dao.RecipeDatabase;
import com.example.myrecipe.models.RecipeTag;
import com.example.myrecipe.models.dao.RecipeTagDAO;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.models.dao.TagDAO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RepositoryCreateRecipe {
    static RepositoryCreateRecipe instance;
    RecipeDAO recipeDAO;
    TagDAO tagDAO;
    IngredientDAO ingredientDAO;
    RecipeTagDAO recipeTagDAO;

    private RepositoryCreateRecipe(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDAO = database.recipeDAO();
        tagDAO = database.tagDAO();
        ingredientDAO = database.ingredientDAO();
        recipeTagDAO = database.recipeTagDAO();
    }

    public static RepositoryCreateRecipe getInstance(Application application){
        if(instance == null){
            instance = new RepositoryCreateRecipe(application);
        }
        return instance;
    }

    //Figures out what tags to add to repository as new tags and which to reuse from the database
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
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Goes through the proccess of inserting the recipe, its tags and its ingredients into the database
    private void insertRecipe(Recipe recipe, List<Tag> newTags, List<Long> associatedTagsIds){
        try {
            long recipeId = new InsertRecipeAsync(recipeDAO).execute(recipe).get();
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.setRecipeId(recipeId);
                new InsertIngredientAsync(ingredientDAO).execute(ingredient);
            }
            if(newTags.size() != 0){
                for (Tag tag : newTags) {
                    associatedTagsIds.add(new InsertTagAsync(tagDAO).execute(tag).get());
                }
            }
            if(associatedTagsIds.size() != 0){
                for (long tagId : associatedTagsIds) {
                    RecipeTag relationship = new RecipeTag(recipeId, tagId);
                    new InsertRecipeTagAsync(recipeTagDAO).execute(relationship);
                }
            }

        } catch (ExecutionException | InterruptedException e) {
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
        TagDAO tagDAO;

        private GetTagNamesAsync(TagDAO tagDAO){
            this.tagDAO = tagDAO;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            return tagDAO.getAllTagNames();
        }
    }

    private class GetTagsAsync extends AsyncTask<Void, Void, List<Tag>>{
        TagDAO tagDAO;

        private GetTagsAsync(TagDAO tagDAO){
            this.tagDAO = tagDAO;
        }

        @Override
        protected List<Tag> doInBackground(Void... voids) {
            return tagDAO.getAllTags();
        }
    }

    private class InsertRecipeAsync extends AsyncTask<Recipe, Void, Long> {
        RecipeDAO recipeDAO;

        private InsertRecipeAsync(RecipeDAO recipeDAO){
            this.recipeDAO = recipeDAO;
        }

        @Override
        protected Long doInBackground(Recipe... recipes) {
            return recipeDAO.insert(recipes[0]);
        }
    }

    private class InsertIngredientAsync extends AsyncTask<Ingredient, Void, Void>{
        IngredientDAO ingredientDAO;

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
        TagDAO tagDAO;

        private InsertTagAsync(TagDAO tagDAO){
            this.tagDAO = tagDAO;
        }

        @Override
        protected Long doInBackground(Tag... tags) {
            return tagDAO.insert(tags[0]);
        }
    }

    private class InsertRecipeTagAsync extends AsyncTask<RecipeTag, Void, Void>{
        RecipeTagDAO recipeTagDAO;

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
