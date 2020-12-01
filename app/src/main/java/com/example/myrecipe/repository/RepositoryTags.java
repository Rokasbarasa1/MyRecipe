package com.example.myrecipe.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myrecipe.models.dao.RecipeDatabase;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.models.dao.TagDAO;

import java.util.List;

import java.util.concurrent.ExecutionException;

public class RepositoryTags {
    private static RepositoryTags instance;
    private TagDAO tagDAO;
    private MutableLiveData<List<Tag>> currentTags;

    private RepositoryTags(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        tagDAO = database.tagDAO();
        currentTags = new MutableLiveData<>();
    }

    public static RepositoryTags getInstance(Application application){
        if(instance == null){
            instance = new RepositoryTags(application);
        }
        return instance;
    }

    public LiveData<List<Tag>> getTags(){
        return currentTags;
    }

    public void getTagsFromDatabase(){
        try {
            currentTags.setValue(new GetAllTagsAsync(tagDAO).execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Tag getTag(int index){
        return currentTags.getValue().get(index);
    }

    private class GetAllTagsAsync extends AsyncTask<Void, Void, List<Tag>> {
        private TagDAO tagDAO;

        private GetAllTagsAsync(TagDAO tagDAO){
            this.tagDAO = tagDAO;
        }

        @Override
        protected List<Tag> doInBackground(Void... voids) {
            return tagDAO.getAllTags();
        }
    }
}
