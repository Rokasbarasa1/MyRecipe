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
    private String lastActivityNameAndQuantity = "nothing";
    private String lastActivityUnits = "nothing";


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
            ingredients = internetRecipeTemplate.getIngredients();
        }else
            ingredients.add(new Ingredient());

        initRecyclerView();

        //Sets a tag if it was obtained from a expanded tag
        tags = rootView.findViewById(R.id.newRecipeTags);
        tags.setText(currentTag);
        return rootView;
    }

    private void initRecyclerView() {
        ingredientList = rootView.findViewById(R.id.rv_new_recipe_ingredients);
        ingredientList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        ingredientAdapter = new AdapterNewRecipeIngredient(ingredients, this);
        ingredientList.setAdapter(ingredientAdapter);
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
        ingredientAdapter.setIngredients(ingredients);
    }

    public void removeLastLineOfIngredient() {
        if(ingredients.size() != 0){
            int index = ingredients.size()-1;
            ingredients.remove(index);
            ingredientAdapter.notifyDataSetChanged();
        }
    }

    public void finishRecipe() {
        //Checks if user filled out all needed fields. Description can be empty
        if(name.getText().toString().matches("")
                || prepTime.getText().toString().matches("")
                || servingSize.getText().toString().matches("")
                || tags.getText().toString().matches("")
                || ingredients.size() == 0
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
                    , ingredients
                    , description.getText().toString()
                    , tags.getText().toString()
                    );
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    //Every time the user enters something into the ingredient sections a ingredient is being attempted to create
    // if it is created it will just update it
    //
    @Override
    public void onEditNameAndQuantity(int position, String textName) {
        if(!textName.matches("") ){
            if(internetRecipeTemplate != null){
                ingredientUpdatedText(position, textName);
            }else {
                ingredientUpdatedText(position, textName);
            }
        }
    }

    @Override
    public void onEditUnits(int position, String text) {
        if(internetRecipeTemplate != null){
            ingredientUpdatedUnits(position, text);
        }else {
            ingredientUpdatedUnits(position, text);
        }
    }

    //These classes ideally should have been in viewmodel. That where it was originaly, but it caused
    // a massive amount of issues
    //
    //Runs every time a user makes a change. Was but was the only way i could save the state of what
    //the user entered before removing a ingredient.
    //Divides what it recieves into name of the ingredient and quantity
    public void ingredientUpdatedText(int index, String text) {
        if(!text.matches("")){
            String[] ingredient = text.split(" ");
            double amount = 0;
            int number = 0;
            for (int i = 0; i < ingredient.length; i++) {
                try{
                    amount = Double.parseDouble(ingredient[i]);
                    number = i;

                }catch(NumberFormatException ignored){
                }
            }
            text = text.substring(0, text.length() - ingredient[number].length());
            Ingredient newIngredient = new Ingredient(text, amount, "");
            try{
                ingredients.get(index);
                ingredients.set(index, newIngredient);
            }catch (IndexOutOfBoundsException e){
                ingredients.add(newIngredient);
            }
        }
    }

    public void ingredientUpdatedUnits(int index, String textUnits) {
        if(!textUnits.matches("")){
            Ingredient newIngredient = new Ingredient("", 0, textUnits);
            try{
                ingredients.get(index);
                newIngredient.setName(ingredients.get(index).getName());
                newIngredient.setQuantity(ingredients.get(index).getQuantity());
                ingredients.set(index, newIngredient);
            }catch (IndexOutOfBoundsException e){
                ingredients.add(newIngredient);
            }
        }
    }
}