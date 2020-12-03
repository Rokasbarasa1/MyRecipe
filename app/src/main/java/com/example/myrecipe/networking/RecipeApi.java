package com.example.myrecipe.networking;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeApi {

    //A non fancy way of getting random recipes using a key i registered on the api.
    @GET("recipes/random?apiKey=524821eb2c5f4c319e1268bad130486d")
    Call<RecipeResponse> getRecipe();
}
