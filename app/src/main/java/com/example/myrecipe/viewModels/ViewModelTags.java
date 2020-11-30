package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myrecipe.models.Tag;
import com.example.myrecipe.repository.RepositoryTags;

import java.util.List;

public class ViewModelTags extends AndroidViewModel {
    private RepositoryTags repo;

    public ViewModelTags(@NonNull Application application) {
        super(application);
        repo = RepositoryTags.getInstance(application);
    }

    public LiveData<List<Tag>> getTags(){
        return repo.getTags();
    }

    public void getTagsFromDatabase(){
        repo.getTagsFromDatabase();
    }

    public Tag getTag(int position) {
        return repo.getTag(position);
    }
}
