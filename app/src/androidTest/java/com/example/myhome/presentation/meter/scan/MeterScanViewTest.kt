package com.example.myhome.presentation.meter.scan

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.launchHiltFragment
import com.example.myhome.presentation.testutils.providers.MeterUITestListProvider
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.myhome.R
import com.example.myhome.presentation.features.meter.scan.MeterScanView
import com.example.myhome.testutils.espresso.withDrawable
import com.example.myhome.testutils.providers.MeterDomainTestListProvider.apartmentList

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MeterScanViewTest: BaseTest() {
    private lateinit var scenario: AutoCloseable
    private val meterArg = MeterUITestListProvider.getMeterParcelableScan(0)

    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchHiltFragment {
            MeterScanView().apply {
                arguments = meterArg.toBundle()
            }
        }
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testMeterInfoDisplayedCorrectly_OnFragmentCreation() {
        onView(withId(R.id.meter_icon))
            .check(matches(withDrawable(R.drawable.gas, R.color.white)))

        onView(withId(R.id.address))
            .check(matches(withText(apartmentList[0].address)))

        onView(withId(R.id.type_of_service_name))
            .check(matches(withText("Газ")))
    }

    @Test
    fun testPreviousReadingDisplayedCorrectly_OnStart() {
        onView(withId(R.id.previous_reading))
            .check(matches(withText("5.867 м3")))
    }

    @Test
    fun testConsumptionDisplayedCorrectly_OnStart() {
        onView(withId(R.id.consumption))
            .check(matches(withText("0")))
    }

    @Test
    fun testConsumptionDisplayedCorrectly_AfterInput() {
        onView(withId(R.id.btn_next))
            .check(matches(not(isEnabled())))

        onView(withId(R.id.btn_7)).perform(click())
        onView(withId(R.id.btn_comma)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_2)).perform(click())
        onView(withId(R.id.btn_0)).perform(click())
        onView(withId(R.id.btn_del)).perform(click())
        onView(withId(R.id.btn_1)).perform(click())

        onView(withId(R.id.btn_next))
            .check(matches(isEnabled()))

        onView(withId(R.id.current_reading))
            .check(matches(withText("7,321")))

        onView(withId(R.id.consumption)) // 7.321 - 5.867
            .check(matches(withText("1.454")))

    }

    @Test
    fun testNextButtonDisabled_AfterSingleInput() {
        onView(withId(R.id.btn_next))
            .check(matches(not(isEnabled())))

        onView(withId(R.id.btn_1)).perform(click())

        onView(withId(R.id.btn_next))
            .check(matches(not((isEnabled()))))
    }

}
