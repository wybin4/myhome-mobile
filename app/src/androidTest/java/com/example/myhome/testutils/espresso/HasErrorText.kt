package com.example.myhome.testutils.espresso

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher

fun layoutHasErrorText(expectedErrorText: String): Matcher<View> {
    return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with error text: $expectedErrorText")
        }

        override fun matchesSafely(item: TextInputLayout): Boolean {
            val error = item.error ?: return expectedErrorText.isEmpty()
            return expectedErrorText == error.toString()
        }
    }
}