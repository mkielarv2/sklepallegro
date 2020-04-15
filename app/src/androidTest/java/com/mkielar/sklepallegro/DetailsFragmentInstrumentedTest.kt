package com.mkielar.sklepallegro

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.mkielar.sklepallegro.view.ListingViewHolder
import com.mkielar.sklepallegro.view.MainActivity
import org.junit.Rule
import org.junit.Test

class DetailsFragmentInstrumentedTest {
    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)

    @Test
    fun testProperDetailLoading() {
        Thread.sleep(1000)
        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ListingViewHolder>(
                0,
                click()
            )
        )
        Thread.sleep(1000)
        onView(withId(R.id.price)).check(matches(isDisplayed()))
            .check(matches(withText("53.00 PLN")))
        onView(withId(R.id.thumbnail)).check(matches(isDisplayed()))
        onView(withId(R.id.webView)).check(matches(isDisplayed()))
    }
}