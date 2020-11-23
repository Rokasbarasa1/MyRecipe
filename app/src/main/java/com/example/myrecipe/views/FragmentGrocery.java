package com.example.myrecipe.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.adapter.AdapterGroceryIngredient;
import com.example.myrecipe.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class FragmentGrocery extends Fragment {
    private RecyclerView ingredientList;
    private AdapterGroceryIngredient adapterGroceryIngredient;
    private List<Ingredient> ingredients;
    private FragmentTransaction ft;


    public FragmentGrocery() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_grocery, container, false);

        //Ingredient shopping bag
        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("Coconut milk", 1f, "can"));
        ingredients.add(new Ingredient("Basmati rice", 300f, "g"));
        ingredients.add(new Ingredient("Chicken", 450f, "g"));
        ingredients.add(new Ingredient("Curry paste", 3, "tablespoons"));
        ingredients.add(new Ingredient("Salt", 1f, "teaspoon"));
        ingredients.add(new Ingredient("Pepper", 0.5f, "teaspoon"));

        ingredientList = rootView.findViewById(R.id.rv_grocery);
        ingredientList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        adapterGroceryIngredient = new AdapterGroceryIngredient(ingredients);
        ingredientList.setAdapter(adapterGroceryIngredient);

        return rootView;
    }
}