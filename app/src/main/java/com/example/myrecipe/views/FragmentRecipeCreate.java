package com.example.myrecipe.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.adapter.AdapterNewRecipeIngredient;
import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.viewModels.ViewModelCreateRecipe;

import java.util.ArrayList;
import java.util.List;

public class FragmentRecipeCreate extends Fragment implements  AdapterNewRecipeIngredient.OnEditTextListener{

    EditText name;
    EditText prepTime;
    EditText servingSize;
    MultiAutoCompleteTextView description;
    MultiAutoCompleteTextView tags;
    RecyclerView ingredientList;
    AdapterNewRecipeIngredient ingredientAdapter;
    List<Ingredient> ingredients;
    ViewModelCreateRecipe viewModel;
    Recipe internetRecipeTemplate;
    String currentTag;
    View rootView;
    SharedPreferences preferences;

    //This constructor is for when the user gets a recipe form the internet and views it.
    //This automaticaly fills the fields of the recipe creation in case the user wants to keep the recipe he got.
    public FragmentRecipeCreate(Recipe internetRecipe) {
        this.internetRecipeTemplate = internetRecipe;
    }

    //A constructor with an option to set a tag. For example the user presses the floating action button
    //in a expanded tag. This would take the tag name which he is in and add it to the tags when creating.
    //Empty string works as well to not set anything.
    public FragmentRecipeCreate(String tagUserWasIn) {
        if(tagUserWasIn != "")
            currentTag = tagUserWasIn + ",";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        //Preferances
        preferences = getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE);

        viewModel = new ViewModelProvider(this).get(ViewModelCreateRecipe.class);

        //Buttons for add and remove ingredient
        rootView.findViewById(R.id.remove_ingredient_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLastLineOfIngredient();
            }
        });
        rootView.findViewById(R.id.add_ingredient_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewLineOfIngredient();
            }
        });

        rootView.findViewById(R.id.finish_ingredient_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishRecipe();
            }
        });

        name = rootView.findViewById(R.id.newRecipeName);
        prepTime = rootView.findViewById(R.id.newRecipePrepTime);
        servingSize = rootView.findViewById(R.id.newRecipeServingSize);
        description = rootView.findViewById(R.id.newRecipeDescription);

        //If the create fragment was oppened from the random fragment, there should be a recipe passed that was
        //obtained from the internet.
        if(internetRecipeTemplate != null)
            setInternetTemplate();

        //Recycler view
        ingredients = new ArrayList<>();

        //Add the ingredients form the recipe that was obtained from the internet
        if(internetRecipeTemplate != null){
            for (int i = 0; i < internetRecipeTemplate.getIngredients().size(); i++) {
                ingredients.add(internetRecipeTemplate.getIngredients().get(i));
            }
        }else
            ingredients.add(new Ingredient());
        ingredientList = rootView.findViewById(R.id.rv_new_recipe_ingredients);
        ingredientList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        ingredientAdapter = new AdapterNewRecipeIngredient(ingredients, this);
        ingredientList.setAdapter(ingredientAdapter);

        //Sets a tag if it was obtained from a expanded tag
        tags = rootView.findViewById(R.id.newRecipeTags);
        tags.setText(currentTag);
        return rootView;
    }

    //Sets the content it obtained from the internet
    private void setInternetTemplate() {
        name.setText(internetRecipeTemplate.getName());
        prepTime.setText(internetRecipeTemplate.getPrepTime() + "");
        servingSize.setText(internetRecipeTemplate.getServingSize() + "");
        description.setText(internetRecipeTemplate.getDescription());
    }

    public void createNewLineOfIngredient(){
        ingredients.add(new Ingredient());
        ingredientAdapter.notifyItemInserted(ingredients.size() - 1);
    }

    public void removeLastLineOfIngredient() {
        if(ingredients.size() != 0){
            ingredients.remove(ingredients.size()-1);
            ingredientAdapter.notifyDataSetChanged();
        }
    }

    public void finishRecipe() {
        //Checks if user filled out all needed fields. Description can be empty
        if(name.getText().toString().matches("")
                || prepTime.getText().toString().matches("")
                || servingSize.getText().toString().matches("")
                || tags.getText().toString().matches("")
                || viewModel.getIngredientsUpdated().size() == 0
        ){
            AlertDialog.Builder deleteDialog = new AlertDialog.Builder(rootView.getContext());
            deleteDialog.setTitle("Error");
            deleteDialog.setMessage("You should make sure you fill all spaces, not including description.");
            deleteDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println("Quit error message");
                }
            });
            deleteDialog.show();
        }else {
            viewModel.addRecipe(name.getText().toString()
                    , prepTime.getText().toString()
                    , servingSize.getText().toString()
                    , description.getText().toString()
                    , tags.getText().toString()
                    );
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    //Every time the user enters something into the ingredient sections a ingredient is being attempted to create
    // if it is created it will just update it
    @Override
    public void onEdit(int position, String textName, String textUnits) {
        viewModel.ingredientUpdated(position, textName, textUnits);
        Ingredient selected = ingredients.get(position);
        selected.setUnitOfMeassure(textUnits);
    }
}