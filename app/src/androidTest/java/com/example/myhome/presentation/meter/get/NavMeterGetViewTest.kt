package com.example.myhome.presentation.meter.get

import android.os.Bundle
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.R
import com.example.myhome.di.MeterDataModule
import com.example.myhome.di.domain.MeterDomainModule
import com.example.myhome.utils.models.MeterGetToScanParcelableModel
import com.example.myhome.utils.models.MeterGetToUpdateParcelableModel
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.launchNavHiltFragment
import com.example.myhome.testutils.providers.MeterUITestListProvider.getMeterParcelableGet
import com.example.myhome.testutils.providers.MeterUITestListProvider.getMeterParcelableScan
import com.example.myhome.testutils.providers.MeterUITestListProvider.getMeterParcelableUpdate
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.argumentCaptor

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(MeterDataModule::class)
class NavMeterGetViewTest: BaseTest() {
    private val navController = mock(NavController::class.java)
    private lateinit var scenario: AutoCloseable

    private val gettedId = 1

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
    fun testUpdateMeterButtonClick_navigateToMeterUpdateView() {
        val expected = getMeterParcelableUpdate(id = gettedId)

        onView(withId(R.id.update_meter_button)).perform(click())

        val argumentCaptor = argumentCaptor<Bundle>()
        verify(navController).navigate(
            eq(R.id.action_meterGetView_to_meterUpdateView),
            argumentCaptor.capture()
        )

        val capturedArgument = argumentCaptor.firstValue
        val meterParcelable = capturedArgument.getParcelable<MeterGetToUpdateParcelableModel>("meter")

        if (meterParcelable != null) {
            assertEquals(expected.meterId, meterParcelable.meterId)
            assertEquals(expected.meterName, meterParcelable.meterName)
        }
    }

    @Test
    fun testScanMeterButtonClick_navigateToMeterScanView() {
        val expected = getMeterParcelableScan(id = gettedId)
        onView(withId(R.id.add_reading_button)).perform(click())

        val argumentCaptor = argumentCaptor<Bundle>()
        verify(navController).navigate(
            eq(R.id.action_meterGetView_to_meterScanView),
            argumentCaptor.capture()
        )

        val capturedArgument = argumentCaptor.firstValue
        val meterParcelable = capturedArgument.getParcelable<MeterGetToScanParcelableModel>("meter")

        if (meterParcelable != null) {
            assertEquals(expected.meterId, meterParcelable.meterId)
            assertEquals(expected.typeOfServiceName, meterParcelable.typeOfServiceName)
        }
    }

}