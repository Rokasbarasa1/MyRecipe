package com.example.myrecipe.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.adapter.AdapterSeeRecipeIngredient;
import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.viewModels.ViewModelRandom;
import com.example.myrecipe.viewModels.ViewModelSeeRecipe;

import java.util.Calendar;
import java.util.List;

public class FragmentRecipeSee extends Fragment implements DialogMakeRecipe.MakeRecipeDialogListener {
    private Recipe selectedRecipe;
    private TextView name;
    private TextView prepTime;
    private TextView servingSize;
    private TextView description;
    private TextView tags;
    private RecyclerView ingredientList;
    private AdapterSeeRecipeIngredient ingredientAdapter;
    private List<Ingredient> ingredients;
    private ViewModelSeeRecipe viewModel;
    private View rootView;
    private String nameOfRecipe;


    public FragmentRecipeSee(String recipe) {
        nameOfRecipe = recipe;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_see_recipe, container, false);

        viewModel = new ViewModelProvider(this).get(ViewModelSeeRecipe.class);
        viewModel.setSelectedRecipe(nameOfRecipe);
        selectedRecipe = viewModel.getRecipe();

        name = rootView.findViewById(R.id.recipeName);
        prepTime = rootView.findViewById(R.id.recipePrepTime);
        servingSize = rootView.findViewById(R.id.recipeServingSize);
        description = rootView.findViewById(R.id.recipeDescription);
        tags = rootView.findViewById(R.id.recipeTags);

        name.setText(selectedRecipe.getName());
        prepTime.setText("Preparation time: " + selectedRecipe.getPrepTime() + " min");
        servingSize.setText("Servings size: " + selectedRecipe.getServingSize() + " servings");
        description.setText(selectedRecipe.getDescription() + "");
        String recipeTags = "";
        for (int i = 0; i < selectedRecipe.getTags().size(); i++) {
            recipeTags +=  selectedRecipe.getTags().get(i).getName();
            if(i < selectedRecipe.getTags().size()-1)
                recipeTags += ",";
        }
        tags.setText(recipeTags);

        ingredients = selectedRecipe.getIngredients();
        ingredientList = rootView.findViewById(R.id.rv_see_recipe_ingredients);
        ingredientList.hasFixedSize();
        ingredientList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        ingredientAdapter = new AdapterSeeRecipeIngredient(ingredients);
        ingredientList.setAdapter(ingredientAdapter);

        final Button makeRecipe = rootView.findViewById(R.id.see_recipe_make_recipe);
        Button deleteRecipe = rootView.findViewById(R.id.see_recipe_delete_recipe);

        makeRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMakeRecipe();
            }
        });
        deleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteRecipe();
            }
        });
        return rootView;
    }

    public void onMakeRecipe(){
        DialogFragment makeRecipeDialog = new DialogMakeRecipe(this);
        makeRecipeDialog.show(getChildFragmentManager(), "makeRecipeDialog");
    }

    public void onDeleteRecipe(){
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(rootView.getContext());
        deleteDialog.setTitle("Delete Recipe");
        deleteDialog.setMessage("Delete " + nameOfRecipe + "?");
        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDeleteRecipe();
                Toast.makeText(rootView.getContext(),"Clicked yes", Toast.LENGTH_SHORT).show();
            }
        });
        deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(rootView.getContext(),"Clicked no", Toast.LENGTH_SHORT).show();
            }
        });
        deleteDialog.show();
    }

    @Override
    public void onDialogPositiveClickGrocery(int servings) {
        System.out.println("Got servings: " + servings);
        viewModel.newGroceryTodo(selectedRecipe.getId(), servings, selectedRecipe.getIngredients().size());
        //Add selected recipe with modified servingsa mouunt
    }

    @Override
    public void onDialogPositiveClickCalendar(Calendar pointInTime) {
        System.out.println("Got date: " + pointInTime.getTime().toString());
        viewModel.newCalendarTodo(selectedRecipe.getId(), pointInTime);
        //Add a date when to eat this recipe
    }

    @Override
    public void onDialogPositiveClickBoth(int servings, Calendar pointInTime) {
        System.out.println("Got servings: " + servings + " and date: " + pointInTime.toString());
        viewModel.newGroceryAndCalendarTodo(selectedRecipe.getId(), servings, selectedRecipe.getIngredients().size(), pointInTime);
        //Add slected recipe with modified servings and calendar date when to eat.
    }
}