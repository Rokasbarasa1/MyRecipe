package com.example.myrecipe;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myrecipe.views.FragmentCalendar;
import com.example.myrecipe.views.FragmentGrocery;
import com.example.myrecipe.views.FragmentRandom;
import com.example.myrecipe.views.FragmentTags;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityMain extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private TextView toolbarTitle;
    private Toolbar toolbar;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("trying to pop stack");
                getSupportFragmentManager().popBackStackImmediate();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbarTitle = findViewById(R.id.toolbar_title);

        //Bottom navigation button
        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        //For backstack fragment management
        fm = getSupportFragmentManager();

        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                System.out.println("Kaka");
            }
        });

        //Set Default fragment
        toolbarTitle.setText("Recipes");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentTags(getSupportFragmentManager(), toolbarTitle, getSupportActionBar())).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;
                    switch (item.getItemId()){
                        case R.id.calendar:
                            toolbarTitle.setText("Calendar");
                            fragment = new FragmentCalendar(getSupportFragmentManager(), toolbarTitle, getSupportActionBar());
                            break;
                        case R.id.recipes:
                            toolbarTitle.setText("Recipes");
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
        return true;
    }
}