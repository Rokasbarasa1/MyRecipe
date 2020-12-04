package com.example.myrecipe.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myrecipe.R;
import com.example.myrecipe.adapter.AdapterGroceryExpandedIngredient;
import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.viewModels.ViewModelGroceryExpanded;

public class FragmentGroceryExpanded extends Fragment implements AdapterGroceryExpandedIngredient.OnExpandedGroceryRecipeClickListener {

    ViewModelGroceryExpanded viewModel;
    Recipe selectedRecipe;
    GroceryTodo groceryTodo;
    RecyclerView ingredientList;
    AdapterGroceryExpandedIngredient adapter;

    public FragmentGroceryExpanded(Recipe selectedRecipe, GroceryTodo groceryTodo) {
        this.selectedRecipe = selectedRecipe;
        this.groceryTodo = groceryTodo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_grocery_expanded, container, false);
        viewModel = new ViewModelProvider(this).get(ViewModelGroceryExpanded.class);

        //Ingredient list
        initIngredientsRecyclerView(rootView);

        //Save button
        rootView.findViewById(R.id.rv_grocery_expanded_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        //Finish button
        rootView.findViewById(R.id.rv_grocery_expanded_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishGroceryList(rootView);
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

    //Reaction to user clicking a checkbox
    @Override
    public void onClick(int position, boolean status) {
        if(status){
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

    //Alert for user trying to finish a grocery todo
    public void finishGroceryList(View rootView){
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(rootView.getContext());
        deleteDialog.setTitle("Finish grocery list");
        deleteDialog.setMessage("Are you sure you want to finish this grocery list?");
        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.finishGroceryList(groceryTodo);
                getActivity().getSupportFragmentManager().popBackStackImmediate();

            }
        });
        deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        deleteDialog.show();
    }

    //Saves the status of the checkboxes
    public void saveChanges(){
        viewModel.saveChangesToStatus(groceryTodo);
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}