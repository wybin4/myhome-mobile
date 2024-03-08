package com.example.myhome.testutils.espresso

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withTextColor(expectedColor: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("with text color: $expectedColor")
        }

        override fun matchesSafely(view: View?): Boolean {
            if (view !is TextView) {
                return false
            }
            val currentColor = view.currentTextColor
            val expectedColorValue = ContextCompat.getColor(view.context, expectedColor)
            return currentColor == expectedColorValue
        }
    }
}
