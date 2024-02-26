package com.example.myhome.presentation.meter.update

import android.app.Activity
import android.app.Instrumentation
import android.provider.Settings
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
import com.example.myhome.R
import com.example.myhome.common.models.DateConverter
import com.example.myhome.di.ApartmentDataModule
import com.example.myhome.di.AppealDataModule
import com.example.myhome.di.SubscriberDataModule
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.espresso.DatePickerListenerViewActions
import com.example.myhome.testutils.espresso.layoutHasErrorText
import com.example.myhome.testutils.launchHiltFragment
import com.example.myhome.testutils.providers.MeterUITestListProvider.getMeterParcelableUpdate
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.Date

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(ApartmentDataModule::class, SubscriberDataModule::class, AppealDataModule::class)
class MeterUpdateViewTest: BaseTest(), DateConverter {
    private lateinit var scenario: AutoCloseable

    private val calendar: Calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val dayOfMonth = 15

    private val meterArg = getMeterParcelableUpdate(0)

    private val expectedDateTime: Date = calendar.apply {
        set(year, month + 1, dayOfMonth)
    }.time
    private val expectedDate = formatDate(expectedDateTime)

    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchHiltFragment {
            MeterUpdateView().apply {
                arguments = meterArg.toBundle()
            }
        }
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
        onView(withId(R.id.verified_at))
            .perform(DatePickerListenerViewActions.setDate(expectedDateTime))

        onView(withId(R.id.verified_at))
            .check(matches(withText(expectedDate)))
    }

    @Test
    fun testIssuedAtDatePicker() {
        onView(withId(R.id.issued_at))
            .perform(DatePickerListenerViewActions.setDate(expectedDateTime))

        onView(withId(R.id.issued_at))
            .check(matches(withText(expectedDate)))
    }

    @Test
    fun testAllFieldsInvalidIssuedAtValid() {
        onView(withId(R.id.issued_at))
            .perform(DatePickerListenerViewActions.setDate(expectedDateTime))

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
    fun testAllFieldsValid_AndNavigateToImagePicker() {
        onView(withId(R.id.verified_at))
            .perform(DatePickerListenerViewActions.setDate(expectedDateTime))

        onView(withId(R.id.issued_at))
            .perform(DatePickerListenerViewActions.setDate(expectedDateTime))

        onView(withId(R.id.next_button))
            .perform(click())

        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        intending(hasAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)).respondWith(result)
    }

}
