package com.example.myrecipe.models.dao;

        import android.content.Context;

        import androidx.room.Database;
        import androidx.room.Room;
        import androidx.room.RoomDatabase;

        import com.example.myrecipe.models.CalendarTodo;
        import com.example.myrecipe.models.GroceryTodo;
        import com.example.myrecipe.models.Ingredient;
        import com.example.myrecipe.models.Recipe;
        import com.example.myrecipe.models.RecipeTag;
        import com.example.myrecipe.models.Tag;

@Database(entities = {Recipe.class, Ingredient.class, Tag.class, RecipeTag.class, GroceryTodo.class, CalendarTodo.class}, version = 5)
public abstract class RecipeDatabase extends RoomDatabase {

    private static RecipeDatabase instance;

    public abstract RecipeDAO recipeDAO();
    public abstract IngredientDAO ingredientDAO();
    public abstract TagDAO tagDAO();
    public abstract RecipeTagDAO recipeTagDAO();
    public abstract GroceryTodoDAO groceryTodoDAO();
    public abstract CalendarTodoDAO calendarTodoDAO();

    public static synchronized RecipeDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RecipeDatabase.class, "recipe_database")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
