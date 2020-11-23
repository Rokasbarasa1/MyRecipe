package com.example.myrecipe.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.myrecipe.models.Ingredient;
import com.example.myrecipe.models.Recipe;
import com.example.myrecipe.models.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagsRepository {
    private static TagsRepository instance;
    private ArrayList<Tag> tagDataSet = new ArrayList<>();
    private ArrayList<Recipe> recipeDataSet = new ArrayList<>();


    public static TagsRepository getInstance(){
        if(instance == null){
            instance = new TagsRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Tag>> getTags(){
        if(tagDataSet.size() == 0){
            setTags();
        }
        MutableLiveData<List<Tag>> data = new MutableLiveData<>();
        data.setValue(tagDataSet);
        return data;
    }

    public Recipe getRecipe(int index){
        return recipeDataSet.get(index);
    }

    public Tag getTag(int index){
        return tagDataSet.get(index);
    }

    private void setTags(){
        /*
        tagDataSet.add(new Tag("Asian"));
        tagDataSet.add(new Tag("Catalan"));
        tagDataSet.add(new Tag("Spanish"));
        tagDataSet.add(new Tag("Savory"));
        tagDataSet.add(new Tag("Sweet"));
        tagDataSet.add(new Tag("Easy"));
        tagDataSet.add(new Tag("Complicated"));
        tagDataSet.add(new Tag("Chicken"));
        tagDataSet.add(new Tag("Beef"));
        tagDataSet.add(new Tag("Pork"));
        tagDataSet.add(new Tag("Complicated"));

         */
        Ingredient ingredient1 = new Ingredient("Basmati rice", 350, "g");
        Ingredient ingredient2 = new Ingredient("Chicken breast", 450, "g");
        Ingredient ingredient3 = new Ingredient("Coconut milk", 200, "g");
        Ingredient ingredient4 = new Ingredient("Curry paste", 40, "g");
        Ingredient ingredient5 = new Ingredient("Butter", 15, "g");


        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient1);
        ingredientList.add(ingredient2);
        ingredientList.add(ingredient3);
        ingredientList.add(ingredient4);
        ingredientList.add(ingredient5);

        Tag tag1 = new Tag("Asian");
        Tag tag2 = new Tag("Chicken");
        Tag tag3 = new Tag("Easy");
        Tag tag4 = new Tag("Rice");

        ArrayList<Tag> tagList = new ArrayList<>();
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

    public void addRecipe(Recipe newRecipe) {
        recipeDataSet.add(newRecipe);
        ArrayList<String> currentTagStrings = new ArrayList<>();
        for (Tag repoTag : tagDataSet) {
            currentTagStrings.add(repoTag.getName());
        }
        for (int i = 0; i < newRecipe.getTags().size(); i++) {
            if(newRecipe.getTags().get(i).getName().equals(""))
                continue;
            if(!currentTagStrings.contains(newRecipe.getTags().get(i).getName())){
                tagDataSet.add(newRecipe.getTags().get(i));
            }
        }
    }

    public MutableLiveData<List<Recipe>> getRecipes() {
        MutableLiveData<List<Recipe>> data = new MutableLiveData<>();
        data.setValue(recipeDataSet);
        return data;
    }

    public MutableLiveData<List<Recipe>> getRecipesByTag(Tag tag) {
        ArrayList<Recipe> selectedRecipes = new ArrayList<>();
        for (int i = 0; i < recipeDataSet.size(); i++) {
            for (int j = 0; j < recipeDataSet.get(i).getTags().size(); j++) {
                if(recipeDataSet.get(i).getTags().get(j).getName().equals(tag.getName()))
                    selectedRecipes.add(recipeDataSet.get(i));
            }
        }
        MutableLiveData<List<Recipe>> data = new MutableLiveData<>();
        data.setValue(selectedRecipes);
        return data;
    }

    public Recipe getRecipeByName(String recipeName) {
        Recipe selectedRecipe = null;
        for (int i = 0; i < recipeDataSet.size(); i++) {
            if(recipeDataSet.get(i).getName().equals(recipeName)){
                selectedRecipe = recipeDataSet.get(i);
                break;
            }
        }
        return selectedRecipe;
    }

    public Recipe getRandomRecipe() {
        if(recipeDataSet.size() != 0){
            int randomNumber = 0 + (int)(Math.random() * recipeDataSet.size());
            return recipeDataSet.get(randomNumber);
        }else
            return null;
    }
}
