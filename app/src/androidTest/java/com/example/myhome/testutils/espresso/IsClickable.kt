package com.example.myhome.testutils.espresso

import android.view.View
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ClickableMatcher(private val expectedClickable: Boolean) : TypeSafeMatcher<View>() {
    override fun matchesSafely(view: View?): Boolean {
        return view?.isClickable == expectedClickable
    }

    override fun describeTo(description: Description?) {
        description?.appendText("view is clickable: $expectedClickable")
    }
}

fun isClickable(expectedClickable: Boolean = true) = ClickableMatcher(expectedClickable)