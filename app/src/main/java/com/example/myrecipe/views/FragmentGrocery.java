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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.adapter.AdapterGroceryRecipe;
import com.example.myrecipe.adapter.AdapterRecipe;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.viewModels.ViewModelGrocery;

import java.util.List;

public class FragmentGrocery extends Fragment implements AdapterRecipe.OnListRecipeClickListener {
    private FragmentManager supportFragmentManager;
    private TextView toolbarTitle;
    private ActionBar upArrow;
    private AdapterGroceryRecipe adapter;
    private ViewModelGrocery viewModel;
    private RecyclerView recipeList;


    public FragmentGrocery(FragmentManager supportFragmentManager, TextView toolbarTitle, ActionBar supportActionBar) {
        this.supportFragmentManager = supportFragmentManager;
        this.toolbarTitle = toolbarTitle;
        this.upArrow = supportActionBar;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarTitle.setText("Grocery");
        upArrow.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_grocery, container, false);

        //Recipes that need groceries list
        initGroceryRecyclerView(rootView);

        return rootView;
    }

    private void initGroceryRecyclerView(View rootView) {
        viewModel = new ViewModelProvider(this).get(ViewModelGrocery.class);

        viewModel.getRecipeGroceries().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setData(recipes, viewModel.getGroceryTodos());
            }
        });
        viewModel.getRecipesForList();

        recipeList = rootView.findViewById(R.id.rv_grocery);
        recipeList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        adapter = new AdapterGroceryRecipe(viewModel.getRecipeGroceries().getValue(), viewModel.getGroceryTodos() ,this);
        recipeList.setAdapter(adapter);
    }

    @Override
    public void onClick(int position, String name) {
        Fragment fragment = null;
        fragment = new FragmentGroceryExpanded(supportFragmentManager, toolbarTitle, upArrow, viewModel.getRecipeGroceries().getValue().get(position), viewModel.getGroceryTodos().get(position));
        toolbarTitle.setText("View ingredients");
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        upArrow.setDisplayHomeAsUpEnabled(true);
    }
}