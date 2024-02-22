package com.example.myhome.testutils.espresso

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withBackgroundTint(@ColorRes colorResId: Int): Matcher<View> {
    return object : BoundedMatcher<View, View>(View::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("with background tint color: ")
            description.appendValue(colorResId)
        }

        override fun matchesSafely(view: View): Boolean {
            val expectedColor = ContextCompat.getColor(view.context, colorResId)
            val backgroundTint = ViewCompat.getBackgroundTintList(view)
            return backgroundTint != null && backgroundTint.defaultColor == expectedColor
        }
    }
}