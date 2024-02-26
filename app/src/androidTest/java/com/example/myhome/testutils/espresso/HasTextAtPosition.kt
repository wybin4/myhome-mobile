package com.example.myhome.testutils.espresso

import android.view.View
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.example.myhome.utils.adapters.SelectorAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun hasTextAtPosition(position: Int, expectedText: String): Matcher<in View> {
    return object : TypeSafeMatcher<Any>() {
        override fun describeTo(description: Description) {
            description.appendText("Adapter item with text: $expectedText")
        }

        override fun matchesSafely(item: Any): Boolean {
            if (item is AppCompatAutoCompleteTextView) {
                val adapter = item.adapter as? SelectorAdapter<*> ?: return false
                val selectedItem = adapter.getItem(position)
                if (selectedItem.name != expectedText) {
                    val actualText = selectedItem.name
                    throw IllegalArgumentException("Expected text: $expectedText, Actual text: $actualText")
                }
                return true
            }
            return false
        }
    }
}