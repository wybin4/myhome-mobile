package com.example.myhome.presentation.meter.list

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.hasTextColor
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.R
import com.example.myhome.di.MeterDataModule
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.espresso.atPosition
import com.example.myhome.testutils.espresso.hasItemCount
import com.example.myhome.testutils.espresso.withDrawable
import com.example.myhome.testutils.launchHiltFragment
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(MeterDataModule::class)
class MeterListViewTest: BaseTest() {
    private lateinit var scenario: AutoCloseable

    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchHiltFragment<MeterListView>()
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testAddMeterButtonVisible() {
        onView(withId(R.id.add_meter_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testMeterListDisplayed() {
        onView(withId(R.id.meter_recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testApartmentListDisplayed() {
        onView(withId(R.id.apartment_recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testApartmentRecyclerViewCountItems() {
        onView(withId(R.id.apartment_recycler_view)).check(matches(hasItemCount(2)))
    }

    @Test
    fun testMeterRecyclerViewItemCountAfterItemClick() {
        // нажали на второй элемент apartment_recycler_view
        onView(withId(R.id.apartment_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        // стало 0 счетчиков
        onView(withId(R.id.meter_recycler_view)).check(matches(hasItemCount(0)))

        // нажали на первый элемент apartment_recycler_view
        onView(withId(R.id.apartment_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        // стало 3 счетчика
        onView(withId(R.id.meter_recycler_view)).check(matches(hasItemCount(3)))
    }

    @Test
    fun testMeterRecyclerViewItems() {
        onView(withId(R.id.meter_recycler_view))
            .check(matches(atPosition(0, allOf(
                hasDescendant(allOf(withId(R.id.type_of_service_name), withText("Газ"))),
                hasDescendant(allOf(withId(R.id.current_reading), withText("16.9 м3"))),
                hasDescendant(allOf(withId(R.id.icon), withDrawable(R.drawable.gas, R.color.red))),
                hasDescendant(allOf(withId(R.id.issued_at), hasTextColor(R.color.red)))
            ))))

        onView(withId(R.id.meter_recycler_view))
            .check(matches(atPosition(2, allOf(
                hasDescendant(allOf(withId(R.id.type_of_service_name), withText("ГВС"))),
                hasDescendant(allOf(withId(R.id.current_reading), withText("11.2 гКал"))),
                hasDescendant(allOf(withId(R.id.icon), withDrawable(R.drawable.water, R.color.primary))),
                hasDescendant(allOf(withId(R.id.issued_at), hasTextColor(R.color.black))) // только светлая тема
            ))))
    }

    @Test
    fun testCurrentReadingIsNull() {
        onView(withId(R.id.meter_recycler_view))
            .check(matches(atPosition(1, allOf(
                hasDescendant(allOf(withId(R.id.current_reading), withText("—"))),
            ))))
    }

}