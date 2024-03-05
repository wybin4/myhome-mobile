package com.example.myhome.presentation.meter

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.MainActivity
import com.example.myhome.R
import com.example.myhome.di.MeterDataModule
import com.example.myhome.presentation.features.meter.add.MeterAddView
import com.example.myhome.presentation.features.meter.update.MeterUpdateView
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.providers.DateProvider
import com.example.myhome.testutils.providers.ImageProvider
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(MeterDataModule::class)
class MeterPresentationModuleTest: BaseTest() {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    override fun setUp() {
        super.setUp()
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testMeterScanView_withInput_andNavigationFlow() {
        // начинаем с точки входа MeterListView
        onView(withId(R.id.meter_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    click()
                )
            ) // нажимаем на 1 элемент, где нет показаний

        onView(withId(R.id.add_reading_button))
            .perform(click()) // открывается MeterGetView, где есть кнопка добавления

        // переходим на MeterScanView
        // вводим показания
        onView(withId(R.id.btn_7)).perform(click())
        onView(withId(R.id.btn_comma)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_2)).perform(click())
        onView(withId(R.id.btn_1)).perform(click())

        onView(withId(R.id.btn_next))
            .perform(click()) // нажимаем "Далее"

        // показания успешно добавляются
        // возвращаемся на MeterGetView
        onView(withId(R.id.back_button))
            .perform(click()) // закрываем окошко

        // проверяем, что показания действительно отобразились на MeterGetView
        onView(withId(R.id.current_reading))
            .check(matches(withText("7.321")))
    }

    @Test
    fun testMeterAddView_withValidInput_andNavigationFlow() {
        // начинаем с точки входа MeterListView
        // нажимаем на кнопку добавления счетчика и переходим на MeterAddView
        onView(withId(R.id.add_meter_button))
            .perform(click())

        // вводим данные
        onView(withId(R.id.factory_number))
            .perform(ViewActions.typeText("12345"), closeSoftKeyboard())

        val dateProvider = DateProvider()
        val imageProvider = ImageProvider()

        scenario.onActivity {
            val fragment: MeterAddView = it.getCurrentFragment() as MeterAddView
            fragment.apply {
                datePickersManager.setIssuedAt(dateProvider.expectedDateTime)
                datePickersManager.setVerifiedAt(dateProvider.expectedDateTime)
                apartmentManager.setIndex(1)
                typeOfServiceManager.setIndex(1)
                imagePicker.publicForActivityResult(imageProvider.bitmap)
            }
        }

        // переход на MeterListView
        // проверяем окошко
        onView(withId(R.id.title))
            .check(matches(withText("Обращение добавлено")))

    }

    @Test
    fun testMeterUpdateView_withValidInput_andNavigationFlow() {
        // начинаем с точки входа MeterListView
        onView(withId(R.id.meter_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            ) // нажимаем на 0 элемент

        // открывается MeterGetView, где есть кнопка перехода на MeterUpdateView
        onView(withId(R.id.update_meter_button))
            .perform(click())

        // вводим данные
        val dateProvider = DateProvider()
        val imageProvider = ImageProvider()

        scenario.onActivity {
            val fragment: MeterUpdateView = it.getCurrentFragment() as MeterUpdateView
            fragment.apply {
                datePickersManager.setIssuedAt(dateProvider.expectedDateTime)
                datePickersManager.setVerifiedAt(dateProvider.expectedDateTime)
                imagePicker.publicForActivityResult(imageProvider.bitmap)
            }
        }

        // переход на MeterGetView
        // проверяем окошко
        onView(withId(R.id.title))
            .check(matches(withText("Обращение добавлено")))

    }

}
