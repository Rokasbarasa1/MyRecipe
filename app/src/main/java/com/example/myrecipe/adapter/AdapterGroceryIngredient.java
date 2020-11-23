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

public class AdapterGroceryIngredient extends RecyclerView.Adapter<AdapterGroceryIngredient.ViewHolder>{
    List<Ingredient> ingredients;
    public AdapterGroceryIngredient(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public AdapterGroceryIngredient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.piece_grocery_ingredient, parent, false);
        return new AdapterGroceryIngredient.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGroceryIngredient.ViewHolder viewHolder, int position) {
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
            name = itemView.findViewById(R.id.ingredient_text_grocery);
        }
    }
}
