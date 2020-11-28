package com.example.myrecipe.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.myrecipe.dao.IngredientDAO;
import com.example.myrecipe.dao.RecipeDAO;
import com.example.myrecipe.dao.RecipeDatabase;
import com.example.myrecipe.dao.RecipeTagDAO;
import com.example.myrecipe.models.Tag;
import com.example.myrecipe.dao.TagDAO;

import java.util.List;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TagsRepository {
    private static TagsRepository instance;
    private RecipeDAO recipeDAO;
    private TagDAO tagDAO;
    private IngredientDAO ingredientDAO;
    private RecipeTagDAO recipeTagDAO;
    private List<Tag> currentTags;

    private TagsRepository(Application application){
        RecipeDatabase database = RecipeDatabase.getInstance(application);
        recipeDAO = database.recipeDAO();
        tagDAO = database.tagDAO();
        ingredientDAO = database.ingredientDAO();
        recipeTagDAO = database.recipeTagDAO();
    }

    public static TagsRepository getInstance(Application application){
        if(instance == null){
            instance = new TagsRepository(application);
        }
        return instance;
    }

    public MutableLiveData<List<Tag>> getTags(){
        try {
            currentTags = new GetAllTagsAsync(tagDAO).execute().get();
            MutableLiveData<List<Tag>> data = new MutableLiveData<>();
            data.setValue(currentTags);
            return data;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MutableLiveData<>();
    }

    public Tag getTag(int index){
        return currentTags.get(index);
    }
/*
    private void setTags(){
        Ingredient ingredient1 = new Ingredient("Basmati rice", 350, "g");
        Ingredient ingredient2 = new Ingredient("Chicken breast", 450, "g");
        Ingredient ingredient3 = new Ingredient("Coconut milk", 200, "g");
        Ingredient ingredient4 = new Ingredient("Curry paste", 40, "g");
        Ingredient ingredient5 = new Ingredient("Butter", 15, "g");


        List<Ingredient> ingredientList = new List<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient3);
        ingredientList.add(ingredient4);
        ingredientList.add(ingredient5);

        Tag tag1 = new Tag("Asian");
        Tag tag2 = new Tag("Chicken");
        Tag tag3 = new Tag("Easy");
        Tag tag4 = new Tag("Rice");

        List<Tag> tagList = new List<>();
        tagList.add(tag1);
        tagList.add(tag2);
        tagList.add(tag3);
        tagList.add(tag4);

        Recipe recipe = new Recipe("Pulled chicken curry", 20, 40, 6, ingredientList,
                "Pretty good dish, easy to make. First boil chicken for 15 min. Take it out and pull it apart with 2 forks. " +
                        "Put rice to boil with equal 1 finger joint rice and 2 finger joint water. Wait for it to start boiling and then turn off heat and cover with lid." +
                        " Put pulled chicken and rice into one bowl or pan. Make sure seasoning is on point with salt and pepper. Use pot you used for rice and chicken to " +
                        "put heat up curry paste. Add some butter so it doesnt burn and then pour coconut milk in. Wait for the mixtureto start boiling, after 3 minutes of boiling " +
                        "pour it on rice and chicken. Should be good."
                , tagList);
        addRecipe(recipe);
    }
 */

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
