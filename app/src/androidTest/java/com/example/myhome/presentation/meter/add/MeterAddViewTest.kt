package com.example.myhome.presentation.meter.add

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.myhome.R
import com.example.myhome.TestFragmentActivity
import com.example.myhome.di.ApartmentDataModule
import com.example.myhome.di.AppealDataModule
import com.example.myhome.di.TypeOfServiceDataModule
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.espresso.hasTextAtPosition
import com.example.myhome.testutils.espresso.layoutHasErrorText
import com.example.myhome.testutils.getFragment
import com.example.myhome.testutils.launchActivityScenario
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getApartmentList
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getTypeOfServiceList
import com.example.myhome.testutils.providers.DateProvider
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(ApartmentDataModule::class, TypeOfServiceDataModule::class, AppealDataModule::class)
class MeterAddViewTest: BaseTest() {
    private lateinit var scenario: ActivityScenario<TestFragmentActivity>
    private lateinit var fragment: MeterAddView

    private val dateProvider = DateProvider()

    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchActivityScenario<MeterAddView>()
        fragment = scenario.getFragment()
        Intents.init()
    }

    @After
    fun tearDown() {
        scenario.close()
        Intents.release()
    }

    @Test
    fun testApartmentSelectorVisibility() {
        onView(withId(R.id.apartment_selector))
            .perform(click())

        onView(withId(R.id.apartment_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testTypeOfServiceSelectorVisibility() {
        onView(withId(R.id.type_of_service_selector))
            .perform(click())

        onView(withId(R.id.type_of_service_list))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testFactoryNumberInputVisibility() {
        onView(withId(R.id.factory_number_input))
            .check(matches(isDisplayed()))
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
    fun testApartmentSelectorText() {
        onView(withId(R.id.apartment_selector))
            .perform(click())

        onView(withId(R.id.apartment_list))
            .check(matches(hasTextAtPosition(
                0, getApartmentList()[0].name
            )))

        onView(withId(R.id.apartment_list))
            .check(matches(hasTextAtPosition(
                1, getApartmentList()[1].name
            )))
    }

    @Test
    fun testTypeOfServiceSelectorText() {
        onView(withId(R.id.type_of_service_selector))
            .perform(click())

        onView(withId(R.id.type_of_service_list))
            .check(matches(hasTextAtPosition(
                2, getTypeOfServiceList()[2].name
            )))

        onView(withId(R.id.type_of_service_list))
            .check(matches(hasTextAtPosition(
                4, getTypeOfServiceList()[4].name
            )))
    }

    @Test
    fun testFactoryNumberInput() {
        val inputText = "12345"
        onView(withId(R.id.factory_number))
            .perform(typeText(inputText), closeSoftKeyboard())

        onView(withId(R.id.factory_number))
            .check(matches(withText(inputText)))
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
    fun testAllFieldsInvalidFactoryNumberValid() {
        onView(withId(R.id.factory_number))
            .perform(typeText("12345"), closeSoftKeyboard())

        onView(withId(R.id.next_button))
            .perform(click())

        onView(withId(R.id.apartment_selector))
            .check(matches(layoutHasErrorText("Выберите квартиру")))

        onView(withId(R.id.type_of_service_selector))
            .check(matches(layoutHasErrorText("Выберите тип услуги")))

        onView(withId(R.id.verified_at_date_picker))
            .check(matches(layoutHasErrorText("Выберите дату поверки")))

        onView(withId(R.id.issued_at_date_picker))
            .check(matches(layoutHasErrorText("Выберите дату истечения")))

        onView(withId(R.id.factory_number_input))
            .check(matches(layoutHasErrorText("")))

    }

    @Test
    fun testAllFieldsInvalid() {
        onView(withId(R.id.next_button))
            .perform(click())

        onView(withId(R.id.apartment_selector))
            .check(matches(layoutHasErrorText("Выберите квартиру")))

        onView(withId(R.id.type_of_service_selector))
            .check(matches(layoutHasErrorText("Выберите тип услуги")))

        onView(withId(R.id.verified_at_date_picker))
            .check(matches(layoutHasErrorText("Выберите дату поверки")))

        onView(withId(R.id.issued_at_date_picker))
            .check(matches(layoutHasErrorText("Выберите дату истечения")))

        onView(withId(R.id.factory_number_input))
            .check(matches(layoutHasErrorText("Введите заводской номер")))

    }

    @Test
    fun testAllFieldsValid_AndRequestGetContent_Successful() {
        onView(withId(R.id.factory_number))
            .perform(typeText("12345"), closeSoftKeyboard())

        runOnUiThread {
            fragment.datePickersManager.setIssuedAt(dateProvider.expectedDateTime)
            fragment.datePickersManager.setVerifiedAt(dateProvider.expectedDateTime)
            fragment.apartmentManager.setIndex(1)
            fragment.typeOfServiceManager.setIndex(1)
        }

        onView(withId(R.id.next_button))
            .perform(click())

        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(result)

    }

}
