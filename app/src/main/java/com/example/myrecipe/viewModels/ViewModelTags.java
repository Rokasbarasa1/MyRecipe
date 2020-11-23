package com.example.myrecipe.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myrecipe.models.Tag;
import com.example.myrecipe.repository.TagsRepository;

import java.util.List;

public class ViewModelTags extends ViewModel {
    private LiveData<List<Tag>> tags;
    private TagsRepository repo;

    public void init(){
        if(tags != null){
            return;
        }
        repo = TagsRepository.getInstance();
        tags = repo.getTags();
    }

    public LiveData<List<Tag>> getTags(){
        return tags;
    }

    public Tag getTag(int position) {
        return repo.getTag(position);
    }

    public void refreshTags() {
        tags = repo.getTags();
    }
}
