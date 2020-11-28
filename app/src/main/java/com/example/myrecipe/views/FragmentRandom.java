package com.example.myrecipe.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.myrecipe.R;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.viewModels.ViewModelCreateRecipe;
import com.example.myrecipe.viewModels.ViewModelRandom;
import com.example.myrecipe.viewModels.ViewModelSeeRecipe;


public class FragmentRandom extends Fragment {
    private View rootView;
    private TextView name;
    private TextView prepTime;
    private TextView cookTime;
    private TextView servingSize;
    private FragmentManager supportFragmentManager;
    private TextView toolbarTitle;
    private ActionBar upArrow;
    private ViewModelRandom viewModel;

    public FragmentRandom(FragmentManager supportFragmentManager, TextView toolbarTitle, ActionBar upArrow) {
        this.supportFragmentManager = supportFragmentManager;
        this.toolbarTitle = toolbarTitle;
        this.upArrow = upArrow;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarTitle.setText("Random");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_random, container, false);

        viewModel = new ViewModelProvider(this).get(ViewModelRandom.class);

        name = rootView.findViewById(R.id.random_recipe_name);
        prepTime = rootView.findViewById(R.id.random_recipe_prepTime);
        cookTime = rootView.findViewById(R.id.random_recipe_cooktime);
        servingSize = rootView.findViewById(R.id.random_recipe_serving);
        name.setText("");
        prepTime.setText("");
        cookTime.setText("");
        servingSize.setText("");

        Button randomize = rootView.findViewById(R.id.random_do_random);
        Button seeRecipe = rootView.findViewById(R.id.random_go_to_recipe);

        randomize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecipe(viewModel.getRandomRecipe());
                upArrow.setDisplayHomeAsUpEnabled(false);
            }
        });

        seeRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString() != ""){
                    Fragment fragment = null;
                    fragment = new FragmentSeeRecipe(name.getText().toString());
                    toolbarTitle.setText("View recipe");
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                    upArrow.setDisplayHomeAsUpEnabled(true);
                }
            }
        });

        return rootView;
    }

    private void setRecipe(Recipe randomRecipe) {
        if(randomRecipe != null){
            name.setText(randomRecipe.getName());
            cookTime.setText("Cook time: " + randomRecipe.getCookTime());
            prepTime.setText("Prep time: " + randomRecipe.getPrepTime());
            servingSize.setText("Serving size: " + randomRecipe.getServingSize());
        }
    }
}