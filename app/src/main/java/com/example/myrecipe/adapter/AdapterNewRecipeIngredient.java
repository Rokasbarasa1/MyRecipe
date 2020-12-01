package com.example.myrecipe.adapter;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecipe.R;
import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.TextChangedListener;

import java.util.List;

public class AdapterNewRecipeIngredient extends RecyclerView.Adapter<AdapterNewRecipeIngredient.ViewHolder>{
    private List<Ingredient> ingredients;
    private OnEditTextListener listener;

    public AdapterNewRecipeIngredient(List<Ingredient> ingredients, OnEditTextListener listener){
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterNewRecipeIngredient.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.piece_create_ingredient, parent, false);
        return new AdapterNewRecipeIngredient.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNewRecipeIngredient.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(ingredients.get(position).getRawString());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.create_ingredient_text);
            name.addTextChangedListener(new TextChangedListener<EditText>(name) {
                @Override
                public void onTextChanged(EditText target, Editable s) {
                    listener.onEdit(getAdapterPosition(), name.getText().toString());
                }
            });
        }
    }

    public interface OnEditTextListener{
        void onEdit(int position, String text);
    }
}
