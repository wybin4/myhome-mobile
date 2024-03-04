package com.example.myhome.presentation.meter.update

import androidx.navigation.NavController
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.myhome.TestFragmentActivity
import com.example.myhome.di.ApartmentDataModule
import com.example.myhome.di.AppealDataModule
import com.example.myhome.di.TypeOfServiceDataModule
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.getFragment
import com.example.myhome.testutils.launchNavActivityScenario
import com.example.myhome.testutils.providers.DateProvider
import com.example.myhome.testutils.providers.ImageProvider
import com.example.myhome.testutils.providers.MeterUITestListProvider
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(ApartmentDataModule::class, TypeOfServiceDataModule::class, AppealDataModule::class)
class ScenarioMeterUpdateViewTest: BaseTest() {
    private lateinit var scenario: ActivityScenario<TestFragmentActivity>
    private lateinit var fragment: MeterUpdateView
    private val navController = Mockito.mock(NavController::class.java)

    private val meterArg = MeterUITestListProvider.getMeterParcelableUpdate(0)

    private val dateProvider = DateProvider()
    private val imageProvider = ImageProvider()

    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchNavActivityScenario<MeterUpdateView>(navController, meterArg.toBundle())
        fragment = scenario.getFragment()
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testAllFieldsValid_AndAppealUpdate_Successful() {
        val fragment = scenario.getFragment<MeterUpdateView>()
        runOnUiThread {
            fragment.datePickersManager.setIssuedAt(dateProvider.expectedDateTime)
            fragment.datePickersManager.setVerifiedAt(dateProvider.expectedDateTime)
            fragment.imagePicker.publicForActivityResult(imageProvider.bitmap)
        }

        verify(navController).popBackStack()
    }

}
