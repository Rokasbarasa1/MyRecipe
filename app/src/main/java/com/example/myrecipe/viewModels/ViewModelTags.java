package com.example.myrecipe.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myrecipe.models.Tag;
import com.example.myrecipe.repository.TagsRepository;

import java.util.List;

public class ViewModelTags extends AndroidViewModel {
    private LiveData<List<Tag>> tags;
    private TagsRepository repo;

    public ViewModelTags(@NonNull Application application) {
        super(application);
        repo = TagsRepository.getInstance(application);
        tags = repo.getTags();
    }

    public LiveData<List<Tag>> getTags(){
        return tags;
    }

    public Tag getTag(int position) {
        return repo.getTag(position);
    }
}
