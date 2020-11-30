package com.example.myrecipe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.models.Recipe;

import java.util.List;

public class AdapterGroceryRecipe extends RecyclerView.Adapter<AdapterGroceryRecipe.ViewHolder>{
    List<Recipe> recipes;
    List<GroceryTodo> groceryTodos;
    AdapterRecipe.OnListRecipeClickListener listener;

    public AdapterGroceryRecipe(List<Recipe> recipes, List<GroceryTodo> groceryTodos, AdapterRecipe.OnListRecipeClickListener listener){
        this.recipes = recipes;
        this.listener = listener;
        this.groceryTodos = groceryTodos;
    }


    public AdapterGroceryRecipe.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.piece_grocery_recipe, parent, false);
        return new AdapterGroceryRecipe.ViewHolder(view);
    }

    public void onBindViewHolder(AdapterGroceryRecipe.ViewHolder viewHolder, int position){
        viewHolder.name.setText(recipes.get(position).getName());
        viewHolder.IngredientCount.setText("Ingredients: " + groceryTodos.get(position).getStatusBitMap().length());
        int count = 0;
        for (int i = 0; i < groceryTodos.get(position).getStatusBitMap().length(); i++) {
            if(groceryTodos.get(position).getStatusBitMap().charAt(i) == '0')
                count++;
        }
        viewHolder.IngredientsLeft.setText("Left to collect: " + count);
    }

    public int getItemCount(){
        if(recipes == null){
            return 0;
        }
        return recipes.size();
    }

    public void setData(List<Recipe> newData, List<GroceryTodo> groceryTodos) {
        this.recipes = newData;
        this.groceryTodos = groceryTodos;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView IngredientCount;
        TextView IngredientsLeft;
        ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), name.getText().toString());
                }
            });
            name = itemView.findViewById(R.id.piece_grocery_recipe_name);
            IngredientCount = itemView.findViewById(R.id.piece_grocery_recipe_ingredient_count);
            IngredientsLeft = itemView.findViewById(R.id.piece_grocery_recipe_ingredient_count_finished);
        }
    }

    public interface OnListRecipeClickListener{
        void onClick(int position, String name);
    }
}
