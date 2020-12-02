package com.example.myrecipe.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecipeApi {

    //A non fancy way of getting random recipes using a key i registered on the api.
    @GET("recipes/random?apiKey=524821eb2c5f4c319e1268bad130486d")
    Call<RecipeResponse> getRecipe();
}
