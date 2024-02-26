package com.example.myhome.presentation.meter.get

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.R
import com.example.myhome.di.MeterDataModule
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.espresso.withBackgroundTint
import com.example.myhome.testutils.espresso.withDrawable
import com.example.myhome.testutils.launchHiltFragment
import com.example.myhome.testutils.providers.MeterUITestListProvider.getMeterParcelableGet
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(MeterDataModule::class)
class ScenarioMeterGetViewTest: BaseTest() {
    private lateinit var scenario: AutoCloseable

    @Test
    fun testAddReadingButtonVisibleWhenCurrentReadingIsNull() {
        val gettedId = 1
        val meterArg = getMeterParcelableGet(id = gettedId).toBundle()

        scenario = launchHiltFragment {
            MeterGetView().apply {
                arguments = meterArg
            }
        }

        onView(withId(R.id.update_meter_button))
            .check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun testAddReadingButtonInvisibleWhenCurrentReadingIsPresent() {
        val gettedId = 2
        val meterArg = getMeterParcelableGet(id = gettedId).toBundle()

        scenario = launchHiltFragment {
            MeterGetView().apply {
                arguments = meterArg
            }
        }

        onView(withId(R.id.add_reading_button))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

        scenario.close()
    }

    @Test
    fun testIssuedDetailsWhenIssued() {
        val gettedId = 0
        val meterArg = getMeterParcelableGet(id = gettedId).toBundle()

        scenario = launchHiltFragment {
            MeterGetView().apply {
                arguments = meterArg
            }
        }

        onView(withId(R.id.issued_card))
            .check(matches(isDisplayed()))
        onView(withId(R.id.issued_true_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.issued_false_text))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.issued_at_icon))
            .check(matches(withDrawable(R.drawable.calendar, R.color.red)))
        onView(withId(R.id.issued_at_icon_background))
            .check(matches(withBackgroundTint(R.color.red_light)))


        scenario.close()
    }

    @Test
    fun testIssuedDetailsWhenNotIssued() {
        val gettedId = 2
        val meterArg = getMeterParcelableGet(id = gettedId).toBundle()

        scenario = launchHiltFragment {
            MeterGetView().apply {
                arguments = meterArg
            }
        }

        onView(withId(R.id.issued_card))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.issued_true_text))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.issued_false_text))
            .check(matches(isDisplayed()))
        onView(withId(R.id.issued_at_icon))
            .check(matches(withDrawable(R.drawable.calendar, R.color.primary)))
        onView(withId(R.id.issued_at_icon_background))
            .check(matches(withBackgroundTint(R.color.primary_light)))


        scenario.close()
    }

}