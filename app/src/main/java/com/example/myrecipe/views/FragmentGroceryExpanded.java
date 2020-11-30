package com.example.myrecipe.views;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myrecipe.R;
import com.example.myrecipe.adapter.AdapterGroceryExpandedIngredient;
import com.example.myrecipe.adapter.AdapterRecipe;
import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.viewModels.ViewModelGrocery;
import com.example.myrecipe.viewModels.ViewModelGroceryExpanded;
import com.example.myrecipe.viewModels.ViewModelTagsExpanded;

public class FragmentGroceryExpanded extends Fragment implements AdapterGroceryExpandedIngredient.OnListRecipeClickListener {
    private FragmentManager supportFragmentManager;
    private TextView toolbarTitle;
    private ActionBar upArrow;
    private ViewModelGroceryExpanded viewModel;
    private Recipe selectedRecipe;
    private GroceryTodo groceryTodo;
    private RecyclerView ingredientList;
    private AdapterGroceryExpandedIngredient adapter;
    private int[] ingredientChanges;


    public FragmentGroceryExpanded(FragmentManager supportFragmentManager, TextView toolbarTitle, ActionBar supportActionBar, Recipe selectedRecipe, GroceryTodo groceryTodo) {
        this.supportFragmentManager = supportFragmentManager;
        this.toolbarTitle = toolbarTitle;
        this.upArrow = supportActionBar;
        this.selectedRecipe = selectedRecipe;
        this.groceryTodo = groceryTodo;
        ingredientChanges = new int[groceryTodo.getStatusBitMap().length()];
        for (int i = 0; i < groceryTodo.getStatusBitMap().length(); i++) {
            ingredientChanges[i] = groceryTodo.getStatusBitMap().charAt(i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_grocery_expanded, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModelGroceryExpanded.class);

        //Ingredient list
        initIngredientsRecyclerView(rootView);

        rootView.findViewById(R.id.rv_grocery_expanded_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
        return rootView;
    }

    private void initIngredientsRecyclerView(View rootView) {
        ingredientList = rootView.findViewById(R.id.rv_grocery_expanded);
        ingredientList.hasFixedSize();
        ingredientList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        adapter = new AdapterGroceryExpandedIngredient(selectedRecipe.getIngredients(), groceryTodo, selectedRecipe, this);
        ingredientList.setAdapter(adapter);
    }

    @Override
    public void onClick(int position, boolean status) {
        if(status == true){
            StringBuilder string = new StringBuilder(groceryTodo.getStatusBitMap());
            string.setCharAt(position, '1');
            groceryTodo.setStatusBitMap(string.toString());
        }
        else {
            StringBuilder string = new StringBuilder(groceryTodo.getStatusBitMap());
            string.setCharAt(position, '0');
            groceryTodo.setStatusBitMap(string.toString());
        }
    }

    public void saveChanges(){
        viewModel.saveChangesToStatus(groceryTodo);
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}