package com.example.myhome.presentation.event.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myhome.R
import com.example.myhome.di.EventDataModule
import com.example.myhome.presentation.features.event.list.EventListView
import com.example.myhome.testutils.BaseTest
import com.example.myhome.testutils.espresso.atPosition
import com.example.myhome.testutils.espresso.clickOnItemInLinearLayout
import com.example.myhome.testutils.espresso.hasItemCount
import com.example.myhome.testutils.launchHiltFragment
import com.example.myhome.testutils.helpers.OptionUiHelper
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(EventDataModule::class)
class EventListViewTest: BaseTest() {
    private lateinit var scenario: AutoCloseable
    private val optionUiHelper = OptionUiHelper()
    
    @Before
    override fun setUp() {
        super.setUp()
        scenario = launchHiltFragment<EventListView>()
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun testEventListDisplayed() {
        onView(withId(R.id.event_recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testEventRecyclerViewCountItems() {
        onView(withId(R.id.event_recycler_view)).check(matches(hasItemCount(7)))
    }

    @Test
    fun testEventRecyclerViewItems() {
        onView(withId(R.id.event_recycler_view))
            .check(matches(atPosition(0, allOf(
                hasDescendant(allOf(withId(R.id.title), withText("Отключение холодной воды"))),
            ))))
        onView(withId(R.id.event_recycler_view))
            .check(matches(atPosition(1, allOf(
                hasDescendant(allOf(withId(R.id.title), withText("Установка домофона с видеонаблюдением")))
            ))))

        optionUiHelper.checkTexts(R.id.proportion, 1, arrayOf("За", "Против", "Воздержусь"))
    }

    @Test
    fun testEventRecyclerViewVotingItem() {
        val positionInEventList = 1
        val selectedPositionForColor = -1
        optionUiHelper.checkConditionalVisibilities(R.id.selected_indicator, positionInEventList, false)
        optionUiHelper.checkConditionalVisibilities(R.id.result_layout, positionInEventList, false)

        optionUiHelper.checkBackgrounds(
            R.id.circle,
            positionInEventList, selectedPositionForColor,
            R.color.primary_light_clicked, R.color.primary
        )
        optionUiHelper.checkTextColors(
            R.id.text,
            positionInEventList, selectedPositionForColor,
            R.color.primary_light_clicked, R.color.primary
        )

        optionUiHelper.checkConditionalVisibilities(R.id.proportion, positionInEventList, false)
        optionUiHelper.checkOptionsClickable(positionInEventList, true)
    }

    @Test
    fun testEventRecyclerViewSelectedVotingItem() {
        val positionInEventList = 2
        val selectedPosition = 0
        optionUiHelper.checkSelectedVisibilities(R.id.selected_indicator, positionInEventList, selectedPosition)
        optionUiHelper.checkBackgrounds(
            R.id.selected_indicator,
            positionInEventList, selectedPosition,
            R.color.primary_light, R.color.primary_light
        )

        optionUiHelper.checkConditionalVisibilities(R.id.result_layout, positionInEventList, false)

        optionUiHelper.checkBackgrounds(
            R.id.circle,
            positionInEventList, selectedPosition,
            R.color.primary_light_clicked, R.color.primary
        )
        optionUiHelper.checkTextColors(
            R.id.text,
            positionInEventList, selectedPosition,
            R.color.primary_light_clicked, R.color.primary
        )

        optionUiHelper.checkTexts(R.id.proportion, positionInEventList, arrayOf("50%", "25%", "25%"))
        optionUiHelper.checkTextColors(
            R.id.proportion,
            positionInEventList, selectedPosition,
            R.color.primary_light_clicked, R.color.primary
        )
        optionUiHelper.checkOptionsClickable(positionInEventList, false)
    }

    @Test
    fun testEventRecyclerViewClosedVotingItem() {
        val positionInEventList = 5
        val selectedPositionForColor = -1
        val selectedPositionForResult = 0
        optionUiHelper.checkConditionalVisibilities(R.id.selected_indicator, positionInEventList, false)
        optionUiHelper.checkSelectedVisibilities(R.id.result_layout, positionInEventList, selectedPositionForResult)

        optionUiHelper.checkBackgrounds(
            R.id.circle,
            positionInEventList, selectedPositionForColor,
            R.color.description_light_clicked, R.color.description_normal
        )
        optionUiHelper.checkTextColors(
            R.id.text,
            positionInEventList, selectedPositionForColor,
            R.color.description_light_clicked, R.color.description_normal
        )

        optionUiHelper.checkTexts(R.id.proportion, positionInEventList, arrayOf("50%", "25%", "25%"))
        optionUiHelper.checkTextColors(
            R.id.proportion,
            positionInEventList, selectedPositionForColor,
            R.color.description_light_clicked, R.color.description_normal
        )
        optionUiHelper.checkOptionsClickable(positionInEventList, false)
    }

    @Test
    fun testEventRecyclerViewSelectedClosedVotingItem() {
        val positionInEventList = 6
        val selectedPosition = 0

        optionUiHelper.checkSelectedVisibilities(R.id.selected_indicator, positionInEventList, selectedPosition)
        optionUiHelper.checkBackgrounds(
            R.id.selected_indicator,
            positionInEventList, selectedPosition,
            R.color.description_light, R.color.description_light
        )

        optionUiHelper.checkSelectedVisibilities(R.id.result_layout, positionInEventList, selectedPosition)

        optionUiHelper.checkBackgrounds(
            R.id.circle,
            positionInEventList, selectedPosition,
            R.color.description_light_clicked, R.color.description_normal
        )
        optionUiHelper.checkTextColors(
            R.id.text,
            positionInEventList, selectedPosition,
            R.color.description_light_clicked, R.color.description_normal
        )

        optionUiHelper.checkTexts(R.id.proportion, positionInEventList, arrayOf("50%", "25%", "25%"))
        optionUiHelper.checkTextColors(
            R.id.proportion,
            positionInEventList, selectedPosition,
            R.color.description_light_clicked, R.color.description_normal
        )

        optionUiHelper.checkOptionsClickable(positionInEventList, false)
    }

    @Test
    fun testEventRecyclerViewAfterSelectVotingItem() {
        val positionInEventList = 1
        val selectedPosition = 1
        optionUiHelper.checkConditionalVisibilities(R.id.proportion, positionInEventList, false)

        onView(withId(R.id.event_recycler_view))
            .perform(clickOnItemInLinearLayout(
                positionInEventList, selectedPosition, R.id.options_layout
            ))

        optionUiHelper.checkConditionalVisibilities(R.id.proportion, positionInEventList, true)
        optionUiHelper.checkTexts(R.id.proportion, positionInEventList, arrayOf("40%", "40%", "20%"))
        optionUiHelper.checkOptionsClickable(positionInEventList, false)
    }

}