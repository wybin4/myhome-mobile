package com.example.myhome.presentation.meter.update

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.myhome.R
import com.example.myhome.TestFragmentActivity
import com.example.myhome.di.AppealDataModule
import com.example.myhome.di.CommonDataModule
import com.example.myhome.presentation.features.meter.update.MeterUpdateView
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.espresso.layoutHasErrorText
import com.example.myhome.testutils.getFragment
import com.example.myhome.testutils.launchActivityScenario
import com.example.myhome.testutils.providers.DateProvider
import com.example.myhome.presentation.testutils.providers.MeterUITestListProvider.getMeterParcelableUpdate
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(CommonDataModule::class, AppealDataModule::class)
class MeterUpdateViewTest: BaseTest() {
    private lateinit var scenario: ActivityScenario<TestFragmentActivity>
    private lateinit var fragment: MeterUpdateView

    private val dateProvider = DateProvider()
    private val meterArg = getMeterParcelableUpdate(0)

    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchActivityScenario  {
            MeterUpdateView().apply {
                arguments = meterArg.toBundle()
            }
        }
        fragment = scenario.getFragment()
        Intents.init()
    }

    @After
    fun tearDown() {
        scenario.close()
        Intents.release()
    }

    @Test
    fun testDisabledMeterInputVisibility() {
        onView(withId(R.id.disabled_meter_input))
            .check(matches(isDisplayed()))

        onView(withId(R.id.meter_field))
            .check(matches(not(isEnabled())))
    }

    @Test
    fun testVerifiedAtDatePickerVisibility() {
        onView(withId(R.id.verified_at_date_picker))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testIssuedAtDatePickerVisibility() {
        onView(withId(R.id.issued_at_date_picker))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNextButtonVisibility() {
        onView(withId(R.id.next_button))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testDisabledMeterInputText() {
        onView(withId(R.id.meter_field))
            .check(matches(withText(meterArg.meterName)))
    }

    @Test
    fun testVerifiedAtDatePicker() {
        runOnUiThread {
            fragment.datePickersManager.setVerifiedAt(dateProvider.expectedDateTime)
        }

        onView(withId(R.id.verified_at))
            .check(matches(withText(dateProvider.expectedDate)))
    }

    @Test
    fun testIssuedAtDatePicker() {
        runOnUiThread {
            fragment.datePickersManager.setIssuedAt(dateProvider.expectedDateTime)
        }

        onView(withId(R.id.issued_at))
            .check(matches(withText(dateProvider.expectedDate)))
    }

    @Test
    fun testAllFieldsInvalidIssuedAtValid() {
        runOnUiThread {
            fragment.datePickersManager.setIssuedAt(dateProvider.expectedDateTime)
        }

        onView(withId(R.id.next_button))
            .perform(click())

        onView(withId(R.id.verified_at_date_picker))
            .check(matches(layoutHasErrorText("Выберите дату поверки")))

        onView(withId(R.id.issued_at_date_picker))
            .check(matches(layoutHasErrorText("")))

        onView(withId(R.id.disabled_meter_input))
            .check(matches(layoutHasErrorText("")))

    }

    @Test
    fun testAllFieldsInvalid() {
        onView(withId(R.id.next_button))
            .perform(click())

        onView(withId(R.id.verified_at_date_picker))
            .check(matches(layoutHasErrorText("Выберите дату поверки")))

        onView(withId(R.id.issued_at_date_picker))
            .check(matches(layoutHasErrorText("Выберите дату истечения")))

        onView(withId(R.id.disabled_meter_input))
            .check(matches(layoutHasErrorText("")))

    }

    @Test
    fun testAllFieldsValid_AndRequestGetContent_Successful() {
        runOnUiThread {
            fragment.datePickersManager.setVerifiedAt(dateProvider.expectedDateTime)
            fragment.datePickersManager.setIssuedAt(dateProvider.expectedDateTime)
        }

        onView(withId(R.id.next_button))
            .perform(click())

        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(result)
    }

}
