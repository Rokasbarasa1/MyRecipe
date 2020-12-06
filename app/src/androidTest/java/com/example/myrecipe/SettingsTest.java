package com.example.myrecipe;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class SettingsTest {

    @Rule
    public ActivityTestRule<ActivityMain> mActivityTestRule = new ActivityTestRule<>(ActivityMain.class);

    @Test
    public void settingsTest(){
        onView(withId(R.id.recipes)).perform(click());
        onView(withId(R.id.settings)).perform(click());
        onView(withId(R.id.toolbar_title)).check(matches(withText("Settings")));
        onView(withText("Google calendar events")).check(matches(isDisplayed()));
        onView(withText("Schedule settings")).check(matches(isDisplayed()));
        onView(withText("Sign out")).check(matches(isDisplayed()));
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.toolbar_title)).check(matches(withText("Cook Book")));
        onView(withId(R.id.add_btn)).check(matches(isDisplayed()));
    }
}
