package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myrecipe.models.CalendarTodo;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.repository.RepositorySchedule;

import java.util.List;

public class ViewModelSchedule extends AndroidViewModel {
    private RepositorySchedule repo;

    public ViewModelSchedule(@NonNull Application application) {
        super(application);
        repo = RepositorySchedule.getInstance(application);
    }

    public LiveData<List<CalendarTodo>> getSchedules() {
        return repo.getSchedules();
    }

    public void getSchedulesFromDatabase() {
        repo.getSchedulesFromDatabase();
    }

    public List<Recipe> getMatchingRecipes() {
        return repo.getMatchingRecipes();
    }
}
