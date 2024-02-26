package com.example.myhome.testutils.espresso

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.example.myhome.utils.listeners.SelectorListener
import org.hamcrest.Matcher

object SelectorListenerViewActions {

    fun setIndex(index: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(SelectorListener::class.java)
            }

            override fun getDescription(): String {
                return "Set index: $index"
            }

            override fun perform(uiController: UiController?, view: View?) {
                val datePickerAdapter = view as SelectorListener
                datePickerAdapter.notifyIndexChanged(index)
            }
        }
    }
}