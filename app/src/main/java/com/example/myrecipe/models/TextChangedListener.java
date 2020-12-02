package com.example.myrecipe.models;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextChangedListener<T> implements TextWatcher {
    //This class was provided by some guy i found on stack overflow.
    // Cant remember his name because I had deleted this class and then got it back from my version
    // control. Couldn't find original question thread.

    //Helps me listen to changes in edit text views. Every time a user enters something it sends a
    //Singnal to my fragment or whatever. I needed this to prevent the destruction of all ingredients
    //When removing one ingredient with the minus button in the recipe creation fragment

    private T target;

    public TextChangedListener(T target) {
        this.target = target;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        this.onTextChanged(target, s);
    }

    public abstract void onTextChanged(T target, Editable s);
}