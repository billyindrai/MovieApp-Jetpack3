package com.billyindrai.subjetpack3

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.ViewPagerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.billyindrai.subjetpack3.ui.home.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FavoriteActivityTest{
    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        IdlingRegistry.getInstance().register(EspressoIdling.getEspressoIdling())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdling.getEspressoIdling())
    }

    @Test
    fun loadFavMovieTv(){
        Espresso.onView(ViewMatchers.withId(R.id.favorite_menu)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.rvMovieFav)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rvMovieFav)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Espresso.onView(ViewMatchers.withId(R.id.viewPagerFav)).perform(ViewPagerActions.scrollRight())

        Espresso.onView(ViewMatchers.withId(R.id.rvTvFav)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rvTvFav)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        Espresso.onView(ViewMatchers.withId(R.id.viewPagerFav)).perform(ViewPagerActions.scrollLeft())

    }
}