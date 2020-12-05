package com.example.myrecipe.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.dao.IngredientDAO;
import com.example.myrecipe.models.dao.RecipeDAO;
import com.example.myrecipe.models.dao.RecipeDatabase;
import com.example.myrecipe.models.dao.TagDAO;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.networking.RecipeApi;
import com.example.myrecipe.networking.RecipeResponse;
import com.example.myrecipe.networking.ServiceGenerator;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryRandomRecipe {

    static RepositoryRandomRecipe instance;
    RecipeDAO recipeDAO;
    TagDAO tagDAO;
    IngredientDAO ingredientDAO;
    MutableLiveData<Recipe> currentRecipe;

    private RepositoryRandomRecipe(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDAO = database.recipeDAO();
        tagDAO = database.tagDAO();
        ingredientDAO = database.ingredientDAO();
        currentRecipe = new MutableLiveData<>();
    }

    public static RepositoryRandomRecipe getInstance(Application application){
        if(instance == null){
            instance = new RepositoryRandomRecipe(application);
        }
        return instance;
    }

    public LiveData<Recipe> getRecipe() {
        return currentRecipe;
    }

    //Gets random recipe from the database
    public void getRandomRecipe() {
        List<Recipe> recipes = null;
        try {
            recipes = new GetAllRecipesAsync(recipeDAO).execute().get();
            if(recipes.size() != 0){
                int randomNumber = (int) (Math.random() * recipes.size());
                currentRecipe.setValue(recipes.get(randomNumber));
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getRandomRecipeFromInternet() {
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
        Call<RecipeResponse> call = recipeApi.getRecipe();
        call.enqueue(new Callback<RecipeResponse>() {
                         @Override
                         public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                             if (response.code() == 200 ) {
                                 currentRecipe.setValue(response.body().getRecipe().getRecipe());
                                 for (int i = 0; i < currentRecipe.getValue().getIngredients().size(); i++) {
                                     Ingredient ingredient = currentRecipe.getValue().getIngredients().get(i);
                                     System.out.println(ingredient.getAsNameQuantityCombo() + " " + ingredient.getUnitOfMeassure());
                                 }
                             }
                         }

                         @Override
                         public void onFailure(Call<RecipeResponse> call, Throwable t) {
                                System.out.println("Something went wrong when getting recipe from the internet");
                                System.out.println(t.getMessage());
                         }
                     }
        );
    }



    private class GetAllRecipesAsync extends AsyncTask<Void, Void, List<Recipe>> {
        RecipeDAO recipeDAO;

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
