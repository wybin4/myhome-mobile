package com.example.myhome.testutils.helpers

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.myhome.R
import com.example.myhome.testutils.espresso.atPosition
import com.example.myhome.testutils.espresso.atPositionInLinearLayout
import com.example.myhome.testutils.espresso.isClickable
import com.example.myhome.testutils.espresso.withBackgroundTint
import com.example.myhome.testutils.espresso.withTextColor
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class OptionUiHelper {
    fun checkTexts(element: Int, position: Int, strings: Array<String>) {
        Espresso.onView(ViewMatchers.withId(R.id.event_recycler_view))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        position, Matchers.allOf(
                            checkText(element, 0, strings),
                            checkText(element, 1, strings),
                            checkText(element, 2, strings)
                        )
                    )
                )
            )
    }

    private fun checkText(element: Int,position: Int, strings: Array<String>): Matcher<View>? {
        return ViewMatchers.hasDescendant(
            atPositionInLinearLayout(
                position,
                ViewMatchers.hasDescendant(
                    Matchers.allOf(
                        ViewMatchers.withId(element),
                        ViewMatchers.withText(strings[position])
                    )
                )
            )
        )
    }

    fun checkOptionsClickable(position: Int, condition: Boolean) {
        Espresso.onView(ViewMatchers.withId(R.id.event_recycler_view))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        position, Matchers.allOf(
                            checkOptionClickable(0, condition),
                            checkOptionClickable(1, condition),
                            checkOptionClickable(2, condition)
                        )
                    )
                )
            )
    }

    private fun checkOptionClickable(position: Int, isClickable: Boolean): Matcher<View>? {
        return when(isClickable) {
            true -> ViewMatchers.hasDescendant(
                atPositionInLinearLayout(
                    position,
                    isClickable()
                )
            )
            false -> return ViewMatchers.hasDescendant(
                atPositionInLinearLayout(
                    position,
                    Matchers.not(isClickable())
                )
            )
        }
    }

    private fun getVisibility(condition: Boolean): ViewMatchers.Visibility {
        return if (condition) ViewMatchers.Visibility.VISIBLE else ViewMatchers.Visibility.GONE
    }

    fun checkConditionalVisibilities(element: Int, parentPosition: Int, condition: Boolean) {
        Espresso.onView(ViewMatchers.withId(R.id.event_recycler_view))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        parentPosition, Matchers.allOf(
                            checkConditionalVisibility(element, 0, condition),
                            checkConditionalVisibility(element, 1, condition),
                            checkConditionalVisibility(element, 2, condition)
                        )
                    )
                )
            )
    }

    private fun checkConditionalVisibility(element: Int, position: Int, condition: Boolean): Matcher<View>? {
        return ViewMatchers.hasDescendant(
            atPositionInLinearLayout(
                position,
                ViewMatchers.hasDescendant(
                    Matchers.allOf(
                        ViewMatchers.withId(element),
                        ViewMatchers.withEffectiveVisibility(getVisibility(condition))
                    )
                )
            )
        )
    }

    fun checkSelectedVisibilities(element: Int, parentPosition: Int, childPosition: Int) {
        Espresso.onView(ViewMatchers.withId(R.id.event_recycler_view))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        parentPosition, Matchers.allOf(
                            checkSelectedVisibility(element, 0, childPosition),
                            checkSelectedVisibility(element, 1, childPosition),
                            checkSelectedVisibility(element, 2, childPosition)
                        )
                    )
                )
            )
    }

    private fun checkSelectedVisibility(element: Int, position: Int, selected: Int): Matcher<View>? {
        return ViewMatchers.hasDescendant(
            atPositionInLinearLayout(
                position,
                ViewMatchers.hasDescendant(
                    Matchers.allOf(
                        ViewMatchers.withId(element),
                        ViewMatchers.withEffectiveVisibility(getVisibility(position == selected))
                    )
                )
            )
        )
    }

    fun checkBackgrounds(
        element: Int,
        parentPosition: Int, selectedPosition: Int,
        unSelectedBackground: Int, selectedBackground: Int
    ) {
        Espresso.onView(ViewMatchers.withId(R.id.event_recycler_view))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        parentPosition, Matchers.allOf(
                            checkBackground(
                                element,
                                0,
                                selectedPosition,
                                unSelectedBackground,
                                selectedBackground
                            ),
                            checkBackground(
                                element,
                                1,
                                selectedPosition,
                                unSelectedBackground,
                                selectedBackground
                            ),
                            checkBackground(
                                element,
                                2,
                                selectedPosition,
                                unSelectedBackground,
                                selectedBackground
                            )
                        )
                    )
                )
            )
    }

    private fun checkBackground(
        element: Int,
        position: Int, selectedPosition: Int,
        unSelectedBackground: Int, selectedBackground: Int
    ): Matcher<View> {
        return if (position == selectedPosition) {
            ViewMatchers.hasDescendant(
                atPositionInLinearLayout(
                    position,
                    ViewMatchers.hasDescendant(
                        Matchers.allOf(
                            ViewMatchers.withId(element), withBackgroundTint(selectedBackground)
                        )
                    )
                )
            )
        } else {
            ViewMatchers.hasDescendant(
                atPositionInLinearLayout(
                    position,
                    ViewMatchers.hasDescendant(
                        Matchers.allOf(
                            ViewMatchers.withId(element), withBackgroundTint(unSelectedBackground)
                        )
                    )
                )
            )
        }
    }

    fun checkTextColors(
        element: Int,
        parentPosition: Int, selectedPosition: Int,
        unSelectedColor: Int, selectedColor: Int
    ) {
        Espresso.onView(ViewMatchers.withId(R.id.event_recycler_view))
            .check(
                ViewAssertions.matches(
                    atPosition(
                        parentPosition, Matchers.allOf(
                            checkTextColor(
                                element,
                                0,
                                selectedPosition,
                                unSelectedColor,
                                selectedColor
                            ),
                            checkTextColor(
                                element,
                                1,
                                selectedPosition,
                                unSelectedColor,
                                selectedColor
                            ),
                            checkTextColor(
                                element,
                                2,
                                selectedPosition,
                                unSelectedColor,
                                selectedColor
                            )
                        )
                    )
                )
            )
    }

    private fun checkTextColor(
        element: Int,
        position: Int, selectedPosition: Int,
        unSelectedColor: Int, selectedColor: Int
    ): Matcher<View> {
        return if (position == selectedPosition) {
            ViewMatchers.hasDescendant(
                atPositionInLinearLayout(
                    position,
                    ViewMatchers.hasDescendant(
                        Matchers.allOf(
                            ViewMatchers.withId(element), withTextColor(selectedColor)
                        )
                    )
                )
            )
        } else {
            ViewMatchers.hasDescendant(
                atPositionInLinearLayout(
                    position,
                    ViewMatchers.hasDescendant(
                        Matchers.allOf(
                            ViewMatchers.withId(element), withTextColor(unSelectedColor)
                        )
                    )
                )
            )
        }
    }
}