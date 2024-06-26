package com.example.myhome.presentation.meter.add

import androidx.navigation.NavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.myhome.R
import com.example.myhome.TestFragmentActivity
import com.example.myhome.di.AppealDataModule
import com.example.myhome.di.CommonDataModule
import com.example.myhome.presentation.features.meter.add.MeterAddView
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.getFragment
import com.example.myhome.testutils.launchNavActivityScenario
import com.example.myhome.testutils.providers.DateProvider
import com.example.myhome.testutils.providers.ImageProvider
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
@UninstallModules(CommonDataModule::class, AppealDataModule::class)
class NavMeterAddViewTest: BaseTest() {
    private lateinit var scenario: ActivityScenario<TestFragmentActivity>
    private lateinit var fragment: MeterAddView
    private val navController = Mockito.mock(NavController::class.java)

    private val dateProvider = DateProvider()
    private val imageProvider = ImageProvider()

    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchNavActivityScenario<MeterAddView>(navController)
        fragment = scenario.getFragment()
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testAllFieldsValid_AndAppealAdd_Successful() {
        onView(withId(R.id.factory_number))
            .perform(typeText("12345"), closeSoftKeyboard())

        val fragment = scenario.getFragment<MeterAddView>()
        runOnUiThread {
            fragment.datePickersManager.setIssuedAt(dateProvider.expectedDateTime)
            fragment.datePickersManager.setVerifiedAt(dateProvider.expectedDateTime)
            fragment.apartmentManager.setIndex(1)
            fragment.typeOfServiceManager.setIndex(1)
            fragment.imagePicker.publicForActivityResult(imageProvider.bitmap)
        }

        verify(navController).popBackStack()

    }

}
