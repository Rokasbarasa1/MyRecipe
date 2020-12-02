package com.example.myrecipe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.models.GroceryTodo;
import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;

import java.util.List;

public class AdapterGroceryExpandedIngredient extends RecyclerView.Adapter<AdapterGroceryExpandedIngredient.ViewHolder>{

    //Handles showing of ingredients once the recipe is clicked in the grocery tab as well as the logic of checkboxes

    private List<Ingredient> ingredients;
    private GroceryTodo todo;
    private AdapterGroceryExpandedIngredient.OnListRecipeClickListener listener;

    public AdapterGroceryExpandedIngredient(List<Ingredient> ingredients, GroceryTodo todo, Recipe recipe, AdapterGroceryExpandedIngredient.OnListRecipeClickListener listener){
        this.ingredients = ingredients;
        this.todo = todo;
        this.listener = listener;

        //Determines the ratio of requested servings to recipe servings
        //Changes all the ingredients of the recipe to reflect the new ratio
        //Doesnt affect the ingredient quantities in the database
        double todoServing = todo.getServingSize();
        double recipeServing = recipe.getServingSize();
        double ratio = todoServing/recipeServing;
        for (int i = 0; i < this.ingredients.size(); i++) {
            double oldValue = this.ingredients.get(i).getQuantity();
            this.ingredients.get(i).setQuantity(oldValue* ratio);
        }
    }

    public AdapterGroceryExpandedIngredient.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.piece_grocery_ingredient, parent, false);
        return new AdapterGroceryExpandedIngredient.ViewHolder(view);
    }

    public void onBindViewHolder(AdapterGroceryExpandedIngredient.ViewHolder viewHolder, int position){
        viewHolder.text.setText(ingredients.get(position).getAsString());
        //Sets the checkbox statuses using the "bitmap"
        if(todo.getStatusBitMap().charAt(position) == '1')
            viewHolder.checkBox.setChecked(true);
        else
            viewHolder.checkBox.setChecked(false);
    }

    public int getItemCount(){
        if(ingredients == null){
            return 0;
        }
        return ingredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        CheckBox checkBox;
        ViewHolder(View itemView){
            super(itemView);
            checkBox = itemView.findViewById(R.id.ingredient_grocery_checkbox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(getAdapterPosition(), checkBox.isChecked());
                }
            });
            text = itemView.findViewById(R.id.ingredient_grocery_name);
        }
    }

    public interface OnListRecipeClickListener{
        void onClick(int position, boolean status);
    }
}
