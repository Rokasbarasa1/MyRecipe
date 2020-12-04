package com.example.myrecipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myrecipe.views.FragmentGrocery;
import com.example.myrecipe.views.FragmentRandom;
import com.example.myrecipe.views.FragmentSchedule;
import com.example.myrecipe.views.FragmentSettings;
import com.example.myrecipe.views.FragmentTags;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
     TextView toolbarTitle;
     Toolbar toolbar;
     MenuItem settingsIcon;
     FirebaseAuth mAuth;
     SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Preferences
        preferences = getSharedPreferences("prefs", MODE_PRIVATE);

        //Firebase authentication
        mAuth = FirebaseAuth.getInstance();

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStackImmediate();
                settingsIcon.setEnabled(true);
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbarTitle = findViewById(R.id.toolbar_title);

        //Bottom navigation button
        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
    }

    //Makes sure the gear icon is still visible after going back stack
    @Override
    protected void onResume() {
        super.onResume();
        if(settingsIcon != null)
            settingsIcon.setEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.AnonymousBuilder().build());

            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.drawable.recipes_big)
                    .build();
            startActivity(signInIntent);
        } else {
            toolbarTitle.setText("Recipes");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentTags(getSupportFragmentManager(), toolbarTitle, getSupportActionBar()))
                    .commit();
        }
    }

    //Points to the settings icon in the toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new FragmentSettings())
                .addToBackStack(null)
                .commit();
        toolbarTitle.setText("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settingsIcon.setEnabled(false);
        return super.onOptionsItemSelected(item);
    }

    //Bottom navigation bar headings
     BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()){
                        case R.id.schedule:
                            toolbarTitle.setText("Schedule");
                            fragment = new FragmentSchedule(getSupportFragmentManager(), toolbarTitle, getSupportActionBar());
                            break;
                        case R.id.recipes:
                            toolbarTitle.setText("Cook Book");
                            fragment = new FragmentTags(getSupportFragmentManager(), toolbarTitle, getSupportActionBar());
                            break;
                        case R.id.grocery:
                            toolbarTitle.setText("Grocery");
                            fragment = new FragmentGrocery(getSupportFragmentManager(), toolbarTitle, getSupportActionBar());
                            break;
                        case R.id.random:
                            toolbarTitle.setText("Random");
                            fragment = new FragmentRandom(getSupportFragmentManager(), toolbarTitle, getSupportActionBar());
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        settingsIcon = menu.getItem(0);
        return true;
    }

    //When user clicks sign out button in settings
    public void signOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        onStart();
                    }
                });
    }

    //When user changes the calendar switch
    public void changeCalendarFunctionality(Object newValue) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Google calendar", newValue + "");
        editor.apply();
    }
}