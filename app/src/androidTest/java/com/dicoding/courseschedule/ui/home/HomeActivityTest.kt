package com.dicoding.courseschedule.ui.home

import android.app.Activity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import org.junit.Rule
import org.junit.Test
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import junit.framework.TestCase

class HomeActivityTest{

    @get:Rule
    var homeActivityRule = ActivityScenarioRule(HomeActivity::class.java)
    var addCourseActivity: Activity? = null

    @Test
    fun homeActivityTest() {
        onView(withId(R.id.action_add)).check(matches(isDisplayed())).perform(click())
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            run {
                addCourseActivity = ActivityLifecycleMonitorRegistry
                    .getInstance()
                    .getActivitiesInStage(Stage.RESUMED).elementAtOrNull(0)
                TestCase.assertTrue(addCourseActivity?.javaClass == AddCourseActivity::class.java)
            }

        }


    }

}