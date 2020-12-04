package com.example.myrecipe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.models.Ingredient;

import java.util.List;

public class AdapterSeeRecipeIngredient extends RecyclerView.Adapter<AdapterSeeRecipeIngredient.ViewHolder>{

    //Handles showing the ingredients when a recipe that is made is shown.
    //It is completely different as im dealing with text boxes here as opposed to edit text's.
    //Need to make sure the user cant edit anything.

    List<Ingredient> ingredients;

    public AdapterSeeRecipeIngredient(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public AdapterSeeRecipeIngredient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.piece_see_ingredient, parent, false);
        return new AdapterSeeRecipeIngredient.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSeeRecipeIngredient.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(ingredients.get(position).getAsString());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.see_ingredient_text);
        }
    }
}
