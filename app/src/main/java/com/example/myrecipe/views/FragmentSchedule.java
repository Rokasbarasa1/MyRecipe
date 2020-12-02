package com.example.myrecipe.views;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.myrecipe.adapter.AdapterSchedule;
import com.example.myrecipe.models.CalendarTodo;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.viewModels.ViewModelSchedule;

import java.util.Calendar;
import java.util.List;

public class FragmentSchedule extends Fragment implements AdapterSchedule.OnClickTextListener{
    private FragmentManager supportFragmentManager;
    private TextView toolbarTitle;
    private ActionBar upArrow;
    private AdapterSchedule adapter;
    private ViewModelSchedule viewModel;
    private RecyclerView scheduleList;
    private List<Recipe> matchingRecipes;
    private int position;

    public FragmentSchedule(FragmentManager supportFragmentManager, TextView toolbarTitle, ActionBar supportActionBar) {
        this.supportFragmentManager = supportFragmentManager;
        this.toolbarTitle = toolbarTitle;
        this.upArrow = supportActionBar;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarTitle.setText("Schedule");
        upArrow.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);

        //Recipes that need groceries list
        initCalendarRecyclerView(rootView);

        return rootView;
    }

    private void initCalendarRecyclerView(View rootView) {
        viewModel = new ViewModelProvider(this).get(ViewModelSchedule.class);

        viewModel.getSchedules().observe(getViewLifecycleOwner(), new Observer<List<CalendarTodo>>() {
            @Override
            public void onChanged(List<CalendarTodo> calendarTodos) {
                matchingRecipes = viewModel.getMatchingRecipes();
                adapter.setData(calendarTodos, matchingRecipes);
                //GO TO POSITION OF TODAY IN that thing
            }
        });

        viewModel.getSchedulesFromDatabase();
        matchingRecipes = viewModel.getMatchingRecipes();

        scheduleList = rootView.findViewById(R.id.rv_schedule);
        scheduleList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        adapter = new AdapterSchedule(viewModel.getSchedules().getValue(), matchingRecipes, this);
        scheduleList.setAdapter(adapter);


        for (int i = 0; i < viewModel.getSchedules().getValue().size(); i++) {
            if(viewModel.getSchedules().getValue().get(i).getCalendarTime().compareTo(Calendar.getInstance()) == 1){
                scheduleList.scrollToPosition(i);
                break;
            }
        }

        if(viewModel.getSchedules().getValue().size() != 0)
            rootView.findViewById(R.id.schedule_empty_notify).setVisibility(View.GONE);
    }

    @Override
    public void onClick(String name) {
        Fragment fragment = null;
        fragment = new FragmentRecipeSee(name);
        toolbarTitle.setText("View recipe");
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        upArrow.setDisplayHomeAsUpEnabled(true);
    }
}