package com.example.myrecipe;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class RandomInternetTest {

    @Rule
    public ActivityTestRule<ActivityMain> mActivityTestRule = new ActivityTestRule<>(ActivityMain.class);

    private void SetUp(){
        onView(withId(R.id.random)).perform(click());
    }

    @Test
    public void randomInternetTest(){
        SetUp();
        onView(withId(R.id.random_do_random_from_internet)).perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.random_go_to_recipe)).perform(click());
        onView(withId(R.id.newRecipePrepTime)).perform(scrollTo());
        onView(withId(R.id.newRecipeServingSize)).perform(scrollTo());
        onView(withId(R.id.newRecipeTags)).perform(scrollTo(), replaceText("Tag"));
        onView(withId(R.id.finish_ingredient_button)).perform(scrollTo(),click());
        onView(withId(R.id.recipes)).perform(click());
        onView(withId(R.id.rv_tags)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_expanded)).perform(actionOnItemAtPosition(0, click()));
        breakDown();
    }

    private void breakDown(){
        onView(withId(R.id.see_recipe_delete_recipe)).perform(scrollTo(), click());
        onView(withId(android.R.id.button1)).perform(scrollTo(), click());
    }
}
