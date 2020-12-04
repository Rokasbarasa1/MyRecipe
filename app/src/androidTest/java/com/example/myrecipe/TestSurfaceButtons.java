package com.example.myrecipe;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestSurfaceButtons {

    @Rule
    public ActivityTestRule<ActivityMain> mActivityTestRule = new ActivityTestRule<>(ActivityMain.class);

    @Test
    public void testSurfaceButtons() {
        //These test are awful to do due to how bad they function in practice. The testing thing always gets lost
        //And cant find things in front of it. Due to this no more of these tests were made
        //Tests buttons of, floating action, up button, schedule, grocery, random, from database, from internet, settings button,

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.schedule)).perform(click());
        onView(withId(R.id.grocery)).perform(click());
        onView(withId(R.id.random)).perform(click());
        onView(withId(R.id.random_do_random)).perform(click());
        onView(withId(R.id.random_do_random_from_internet)).perform(click());
        onView(withId(R.id.recipes)).perform(click());
        onView(withId(R.id.add_btn)).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.schedule)).perform(click());
        onView(withId(R.id.grocery)).perform(click());
        onView(withId(R.id.settings)).perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
