package com.mkielar.sklepallegro

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.mkielar.sklepallegro.Utils.withViewAt
import com.mkielar.sklepallegro.view.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test


class MainFragmentInstrumentedTest {
    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Test
    fun testDisplayingItems() {
        onView(withId(R.id.recycler)).check(matches(isDisplayed()))

        Thread.sleep(1000)

        onView(withId(R.id.recycler)).check(
            matches(
                withViewAt(1, isDisplayed())
            )
        )

        onView(withId(R.id.recycler)).check(
            matches(
                withViewAt(2, isDisplayed())
            )
        )
    }
}
