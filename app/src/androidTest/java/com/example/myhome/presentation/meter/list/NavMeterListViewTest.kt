package com.example.myhome.presentation.meter.list

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.R
import com.example.myhome.di.domain.MeterDomainModule
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.launchNavHiltFragment
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
@UninstallModules(MeterDomainModule::class)
class NavMeterListViewTest: BaseTest() {

    private lateinit var scenario: AutoCloseable
    private val navController = mock(NavController::class.java)

    @Before
    override fun setUp() {
        super.setUp()

        scenario = launchNavHiltFragment<MeterListView>(navController)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testAddMeterButtonClick_navigateToMeterAddView() {
        onView(withId(R.id.add_meter_button)).perform(click())

        verify(navController).navigate(
            R.id.action_MeterListView_to_MeterAddView
        )
    }

}