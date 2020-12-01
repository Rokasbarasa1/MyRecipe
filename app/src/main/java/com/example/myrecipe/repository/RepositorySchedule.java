package com.example.myrecipe.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myrecipe.models.dao.CalendarTodoDAO;
import com.example.myrecipe.models.dao.RecipeDAO;
import com.example.myrecipe.models.dao.RecipeDatabase;
import com.example.myrecipe.models.CalendarTodo;
import com.example.myrecipe.models.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RepositorySchedule {
    private static RepositorySchedule instance;
    private RecipeDAO recipeDAO;
    private CalendarTodoDAO calendarTodoDAO;
    private MutableLiveData<List<CalendarTodo>> schedules;

    private RepositorySchedule(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDAO = database.recipeDAO();
        calendarTodoDAO = database.calendarTodoDAO();
        schedules = new MutableLiveData<>();
    }

    public static RepositorySchedule getInstance(Application application){
        if(instance == null){
            instance = new RepositorySchedule(application);
        }
        return instance;
    }

    public LiveData<List<CalendarTodo>> getSchedules() {
        return schedules;
    }

    public void getSchedulesFromDatabase() {
        try {
            schedules.setValue(new GetAllSchedulesAsync(calendarTodoDAO).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getMatchingRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        try {
            recipes = new GetMatchRecipeToScheduleAsync(recipeDAO).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private class GetAllSchedulesAsync extends AsyncTask<Void, Void, List<CalendarTodo>> {
        private CalendarTodoDAO calendarTodoDAO;

        private GetAllSchedulesAsync(CalendarTodoDAO calendarTodoDAO){
            this.calendarTodoDAO = calendarTodoDAO;
        }

        @Override
        protected List<CalendarTodo> doInBackground(Void... voids) {
            return calendarTodoDAO.getAllSchedules();
        }
    }

    private class GetMatchRecipeToScheduleAsync extends AsyncTask<Void, Void, List<Recipe>> {
        private RecipeDAO recipeDAO;

        private GetMatchRecipeToScheduleAsync(RecipeDAO recipeDAO){
            this.recipeDAO = recipeDAO;
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            return recipeDAO.getMatchToSchedule();
        }
    }
}
