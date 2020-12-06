package com.example.myrecipe.viewModels;

import android.app.Application;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.repository.RepositoryCreateRecipe;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewModelCreateRecipeTest {

    @Mock
    Application application;
    @Mock
    RepositoryCreateRecipe repo;

    ViewModelCreateRecipe viewModelCreateRecipe;

    @Before
    public void setUp() throws Exception {
        when(RepositoryCreateRecipe.getInstance(application)).thenReturn(repo);
        when(repo.getTags()).thenReturn(getTagsStub());
        viewModelCreateRecipe = new ViewModelCreateRecipe(application);
    }

    private List<Tag> getTagsStub() {
        Tag tag1 = new Tag("Beef");
        Tag tag2 = new Tag("Chicken");
        Tag tag3 = new Tag("Lamb");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);

        tagList.get(0).setId(1);
        tagList.get(1).setId(2);
        tagList.get(2).setId(3);
        return tagList;
    }

    @Test
    public void addRecipeTest(){
        Ingredient ingredient1 = new Ingredient("1", 1, "g");
        Ingredient ingredient2 = new Ingredient("2", 2, "g");
        Ingredient ingredient3 = new Ingredient("3", 3, "g");
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient3);

        String tags = "Beef," + "Chicken" + "Lamb";
        ArgumentCaptor<Recipe> argument = ArgumentCaptor.forClass(Recipe.class);
        //As ive seen from examples this should work but my compiler is complaining
        //when(repo.addRecipe(argument.capture())).then();
        viewModelCreateRecipe.addRecipe("Name", "1", "1", ingredientList, "Description", tags);

    }
}
