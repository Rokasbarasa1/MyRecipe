package com.example.myrecipe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.models.Recipe;

import java.util.List;

public class AdapterRecipe extends RecyclerView.Adapter<AdapterRecipe.ViewHolder> {

    //Just shows recipes once the tag is expanded in the recipe section

    List<Recipe> recipes;
    OnListRecipeClickListener listener;

    public AdapterRecipe(List<Recipe> recipes, OnListRecipeClickListener listener){
        this.recipes = recipes;
        this.listener = listener;
    }


    public AdapterRecipe.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.piece_recipe, parent, false);
        return new AdapterRecipe.ViewHolder(view);

    }

    public void onBindViewHolder(AdapterRecipe.ViewHolder viewHolder, int position){
        viewHolder.name.setText(recipes.get(position).getName());
        viewHolder.prepTime.setText("Prep time: " + recipes.get(position).getPrepTime());
        viewHolder.servings.setText("Serving size: " + recipes.get(position).getServingSize());
    }

    public int getItemCount(){
        if(recipes == null){
            return 0;
        }
        return recipes.size();
    }

    public void setData(List<Recipe> newData) {
        this.recipes = newData;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView prepTime;
        TextView servings;
        ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), recipes.get(getAdapterPosition()).getId());
                }
            });
            name = itemView.findViewById(R.id.piece_recipe_name);
            prepTime = itemView.findViewById(R.id.piece_recipe_prepTime);
            servings = itemView.findViewById(R.id.piece_recipe_serving);
        }
    }

    public interface OnListRecipeClickListener{
        void onClick(int position, long recipeId);
    }
}
