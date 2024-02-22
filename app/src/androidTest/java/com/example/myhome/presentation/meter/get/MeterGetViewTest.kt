package com.example.myhome.presentation.meter.get

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.R
import com.example.myhome.di.domain.MeterDomainModule
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.espresso.hasItemCount
import com.example.myhome.testutils.launchNavHiltFragment
import com.example.myhome.testutils.providers.MeterUITestListProvider.getMeterParcelableGet
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(MeterDomainModule::class)
class MeterGetViewTest: BaseTest() {
    private val navController = Mockito.mock(NavController::class.java)
    private lateinit var scenario: AutoCloseable

    private val gettedId = 2

    private val meterGetArg = getMeterParcelableGet(id = gettedId)

    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchNavHiltFragment<MeterGetView>(navController, meterGetArg.toBundle())
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testUpdateMeterButtonVisible() {
        onView(withId(R.id.update_meter_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testReadingListDisplayed() {
        onView(withId(R.id.reading_recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testReadingRecyclerViewCountItems() {
        onView(withId(R.id.reading_recycler_view)).check(matches(hasItemCount(4)))
    }

}