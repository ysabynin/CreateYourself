package com.kozsabynin.createyourself;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.kozsabynin.createyourself.activity.LoginActivity;
import com.kozsabynin.createyourself.activity.SignupActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.CharMatcher.is;
import static android.support.test.espresso.core.deps.guava.base.Predicates.not;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {
    @Rule
    public ActivityTestRule<LoginActivity> mLoginRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public ActivityTestRule<SignupActivity> mSignupRule =
            new ActivityTestRule<>(SignupActivity.class);

    @Test
    public void goToSignUpPage() {
        mLoginRule.launchActivity(new Intent());
        onView(withId(R.id.textViewSignUp)).perform(click());
        onView(withId(R.id.buttonSignup)).check(matches(isDisplayed()));
    }

    @Test
    public void goToLoginPage() {
        mSignupRule.launchActivity(new Intent());
        onView(withId(R.id.textViewSignin)).perform(click());
        onView(withId(R.id.buttonSignin)).check(matches(isDisplayed()));
    }

    @Test
    public void checkEmptyLogin() {
        mLoginRule.launchActivity(new Intent());
        onView(withId(R.id.buttonSignin)).perform(click());
        onView(withId(R.id.buttonSignin)).check(matches(isDisplayed()));
    }
}