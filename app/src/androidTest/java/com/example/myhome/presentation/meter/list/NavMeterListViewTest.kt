package com.example.myhome.presentation.meter.list

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.R
import com.example.myhome.di.MeterDataModule
import com.example.myhome.utils.models.MeterListToGetParcelableModel
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.launchNavHiltFragment
import com.example.myhome.testutils.providers.MeterUITestListProvider.getMeterParcelableGet
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.argumentCaptor

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(MeterDataModule::class)
class NavMeterListViewTest: BaseTest() {
    private val navController = mock(NavController::class.java)
    private lateinit var scenario: AutoCloseable

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testAddMeterButtonClick_navigateToMeterAddView() {
        scenario = launchNavHiltFragment<MeterListView>(navController)

        onView(withId(R.id.add_meter_button)).perform(click())

        verify(navController).navigate(
            R.id.action_MeterListView_to_MeterAddView
        )
    }

    @Test
    fun clickMeterRecyclerViewItem_navigateToMeterGetView() {
        scenario = launchNavHiltFragment<MeterListView>(navController)
        
        val expected = getMeterParcelableGet(id = 0)

        onView(withId(R.id.meter_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        val argumentCaptor = argumentCaptor<Bundle>()
        verify(navController).navigate(
            eq(R.id.action_MeterListView_to_MeterGetView),
            argumentCaptor.capture()
        )

        val capturedArgument = argumentCaptor.firstValue
        val meterParcelable = capturedArgument.getParcelable<MeterListToGetParcelableModel>("meter")

        if (meterParcelable != null) {
            assertEquals(expected.id, meterParcelable.id)
            assertEquals(expected.typeOfServiceName, meterParcelable.typeOfServiceName)
        }

    }

}