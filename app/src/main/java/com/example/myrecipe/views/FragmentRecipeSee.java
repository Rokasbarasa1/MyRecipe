package com.example.myrecipe.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.adapter.AdapterSeeRecipeIngredient;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.viewModels.ViewModelSeeRecipe;

import java.util.Calendar;

public class FragmentRecipeSee extends Fragment implements DialogMakeRecipe.MakeRecipeDialogListener {

    Recipe selectedRecipe;
    RecyclerView ingredientList;
    AdapterSeeRecipeIngredient ingredientAdapter;
    ViewModelSeeRecipe viewModel;
    View rootView;
    long recipeId;
    SharedPreferences preferences;

    public FragmentRecipeSee(long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_see_recipe, container, false);

        //Preferances
        preferences = getActivity().getSharedPreferences("prefs", getActivity().MODE_PRIVATE);

        viewModel = new ViewModelProvider(this).get(ViewModelSeeRecipe.class);
        viewModel.setSelectedRecipe(recipeId);
        selectedRecipe = viewModel.getRecipe();

        TextView name = rootView.findViewById(R.id.recipeName);
        TextView prepTime = rootView.findViewById(R.id.recipePrepTime);
        TextView servingSize = rootView.findViewById(R.id.recipeServingSize);
        TextView description = rootView.findViewById(R.id.recipeDescription);
        TextView tags = rootView.findViewById(R.id.recipeTags);

        name.setText(selectedRecipe.getName());
        prepTime.setText(selectedRecipe.getPrepTime() + " min");
        servingSize.setText(selectedRecipe.getServingSize() + " servings");
        description.setText(selectedRecipe.getDescription() + "");
        String recipeTags = "";
        for (int i = 0; i < selectedRecipe.getTags().size(); i++) {
            recipeTags +=  selectedRecipe.getTags().get(i).getName();
            if(i < selectedRecipe.getTags().size()-1)
                recipeTags += ",";
        }
        tags.setText(recipeTags);

        ingredientList = rootView.findViewById(R.id.rv_see_recipe_ingredients);
        ingredientList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        ingredientAdapter = new AdapterSeeRecipeIngredient(selectedRecipe.getIngredients());
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

    //Adds recipe either to grocery section, schedule section or both. In app settings. If google calendar
    // is switched on it will create an event for google calendar at the same time when schedule
    public void onMakeRecipe(){
        DialogFragment makeRecipeDialog = new DialogMakeRecipe(this);
        makeRecipeDialog.show(getChildFragmentManager(), "makeRecipeDialog");
    }

    public void onDeleteRecipe(){
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(rootView.getContext());
        deleteDialog.setTitle("Delete Recipe");
        deleteDialog.setMessage("Delete " + selectedRecipe.getName() + "?");
        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.onDeleteRecipe(selectedRecipe.getId());
                getActivity().getSupportFragmentManager().popBackStackImmediate();
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
        viewModel.newGroceryTodo(selectedRecipe.getId(), servings, selectedRecipe.getIngredients().size());
    }

    @Override
    public void onDialogPositiveClickCalendar(Calendar pointInTime) {
        Calendar endTime = pointInTime;
        endTime.add(Calendar.MINUTE, selectedRecipe.getPrepTime());
        if(preferences.getString("Google calendar", "false").equals("true")){
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, pointInTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, selectedRecipe.getName())
                    .putExtra(CalendarContract.Events.DESCRIPTION, "You planned to make this dish");
            startActivity(intent);
        }

        viewModel.newCalendarTodo(selectedRecipe.getId(), pointInTime);
    }

    @Override
    public void onDialogPositiveClickBoth(int servings, Calendar pointInTime) {
        Calendar endTime = pointInTime;
        endTime.add(Calendar.MINUTE, selectedRecipe.getPrepTime());

        if(preferences.getString("Google calendar", "false").equals("true")){
            Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, pointInTime.getTimeInMillis())
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                    .putExtra(CalendarContract.Events.TITLE, selectedRecipe.getName())
                    .putExtra(CalendarContract.Events.DESCRIPTION, "You planned to make this dish");
            startActivity(intent);
        }
        viewModel.newGroceryAndCalendarTodo(selectedRecipe.getId(), servings, selectedRecipe.getIngredients().size(), pointInTime);
    }
}