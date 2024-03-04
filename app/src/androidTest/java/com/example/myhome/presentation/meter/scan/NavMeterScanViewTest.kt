package com.example.myhome.presentation.meter.scan

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.R
import com.example.myhome.di.MeterDataModule
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.launchNavHiltFragment
import com.example.myhome.testutils.providers.MeterUITestListProvider.getMeterParcelableScan
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(MeterDataModule::class)
class NavMeterScanViewTest: BaseTest() {
    private val navController = mock(NavController::class.java)
    private lateinit var scenario: AutoCloseable

    private val meterScanArg = getMeterParcelableScan(0)

    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchNavHiltFragment<MeterScanView>(navController, meterScanArg.toBundle())
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun clickBtnNextWithValidReading_navigateToMeterScanView() {
        onView(withId(R.id.btn_7)).perform(click())
        onView(withId(R.id.btn_comma)).perform(click())
        onView(withId(R.id.btn_3)).perform(click())
        onView(withId(R.id.btn_2)).perform(click())
        onView(withId(R.id.btn_1)).perform(click())

        onView(withId(R.id.btn_next))
            .perform(click())

        verify(navController).popBackStack()

    }

}