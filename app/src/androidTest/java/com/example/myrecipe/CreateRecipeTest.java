package com.example.myrecipe;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class CreateRecipeTest {

    @Rule
    public ActivityTestRule<ActivityMain> mActivityTestRule = new ActivityTestRule<>(ActivityMain.class);

    private void SetUp(){
        onView(withId(R.id.recipes)).perform(click());
    }

    @Test
    public void createRecipeTest(){
        SetUp();
        onView(withId(R.id.add_btn)).perform(click());
        onView(withId(R.id.toolbar_title)).check(matches(withText("Create recipe")));
        onView(withId(R.id.textView4)).check(matches(withText("Prep time:")));
        onView(withText("Ingredients")).check(matches(withText("Ingredients")));
        onView(withId(R.id.add_ingredient_button)).check(matches(isDisplayed()));
        onView(withId(R.id.remove_ingredient_button)).check(matches(isDisplayed()));
        onView(withText("Description/Steps")).check(matches(withText("Description/Steps")));
        onView(withText("Tags")).check(matches(withText("Tags")));
        onView(withId(R.id.finish_ingredient_button)).check(matches(isDisplayed()));
        onView(withContentDescription("Navigate up")).check(matches(isDisplayed()));
        onView(withId(R.id.settings)).check(matches(withText("")));
        onView(withId(R.id.newRecipeName)).perform(scrollTo(), replaceText("1"), closeSoftKeyboard());
        onView(withId(R.id.newRecipePrepTime)).perform(scrollTo(), replaceText("1"), closeSoftKeyboard());
        onView(withId(R.id.newRecipeServingSize)).perform(scrollTo(), replaceText("1"), closeSoftKeyboard());


        onView(withId(R.id.create_ingredient_text)).perform(replaceText("ingredient 1"), closeSoftKeyboard());
        onView(withId(R.id.create_ingredient_unit)).perform(replaceText("grams"), closeSoftKeyboard());
        onView(withId(R.id.add_ingredient_button)).perform(scrollTo(), click());
        //Due to limitations of espresso i could not add information to the second field.
        //Very disappointing because this is very important and problematic functionality
        onView(withId(R.id.remove_ingredient_button)).perform(scrollTo(), click());

        onView(withId(R.id.newRecipeDescription)).perform(replaceText("Description"));
        onView(withId(R.id.newRecipeTags)).perform(scrollTo(), replaceText("Tag"), closeSoftKeyboard());
        onView(withId(R.id.finish_ingredient_button)).perform(scrollTo(), click());
        BreakDown();
    }

    private void BreakDown(){
        onView(withId(R.id.rv_tags)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rv_expanded)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.see_recipe_delete_recipe)).perform(scrollTo(), click());
        onView(withId(android.R.id.button1)).perform(scrollTo(), click());
    }
}
