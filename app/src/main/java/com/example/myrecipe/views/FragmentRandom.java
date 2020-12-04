package com.example.myrecipe.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myrecipe.R;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.viewModels.ViewModelRandom;


public class FragmentRandom extends Fragment {

    View rootView;
    TextView name;
    TextView prepTime;
    TextView servingSize;
    FragmentManager supportFragmentManager;
    TextView toolbarTitle;
    ActionBar upArrow;
    ViewModelRandom viewModel;
    Long recipeId;
    boolean randomFromInternet;

    public FragmentRandom(FragmentManager supportFragmentManager, TextView toolbarTitle, ActionBar upArrow) {
        this.supportFragmentManager = supportFragmentManager;
        this.toolbarTitle = toolbarTitle;
        this.upArrow = upArrow;
        randomFromInternet = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarTitle.setText("Random");
        upArrow.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_random, container, false);

        viewModel = new ViewModelProvider(this).get(ViewModelRandom.class);

        name = rootView.findViewById(R.id.random_recipe_name);
        prepTime = rootView.findViewById(R.id.random_recipe_prepTime);
        servingSize = rootView.findViewById(R.id.random_recipe_serving);
        name.setText("Get a random recipe by clicking a button bellow");
        prepTime.setText("");
        servingSize.setText("");

        viewModel.getRecipe().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                setRecipe(recipe);
            }
        });
        rootView.findViewById(R.id.random_do_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getRandomRecipe();
                upArrow.setDisplayHomeAsUpEnabled(false);
                randomFromInternet = false;
            }
        });

        //Listens to make recipe button. Checks what button was pressed before to determine wheather to show
        // a recipe from the system or the internet.
        rootView.findViewById(R.id.random_go_to_recipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString() != ""){
                    if(randomFromInternet){
                        Fragment fragment = null;
                        fragment = new FragmentRecipeCreate(viewModel.getRecipe().getValue());
                        toolbarTitle.setText("Create recipe");
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                        upArrow.setDisplayHomeAsUpEnabled(true);
                    }else {
                        Fragment fragment = null;
                        fragment = new FragmentRecipeSee(recipeId);
                        toolbarTitle.setText("View recipe");
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                        upArrow.setDisplayHomeAsUpEnabled(true);
                    }
                }
            }
        });

        rootView.findViewById(R.id.random_do_random_from_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getRandomRecipeFromInternet();
                randomFromInternet = true;
            }
        });
        return rootView;
    }

    //Sets recipe in the empty spot.
    private void setRecipe(Recipe randomRecipe) {
        if(randomRecipe != null){
            recipeId = randomRecipe.getId();
            name.setText(randomRecipe.getName());
            prepTime.setText("Prep time: " + randomRecipe.getPrepTime() + " min");
            servingSize.setText("Serving size: " + randomRecipe.getServingSize() + " servings");
        }
    }
}