package com.k4kya.customviews;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class FormFieldViewTest {

    @Rule
    public ActivityTestRule activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testFormFieldValidates() {
        onView(
            allOf(
                withClassName(endsWith("EditText")),
                withHint(endsWith("name"))))
            .perform(click())
            .perform(typeText("Amal"));
        onView(
            withId(R.id.btn_next)).
            check(matches(isEnabled()));
    }
}