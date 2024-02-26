package com.example.myhome.testutils.espresso

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.example.myhome.utils.listeners.DatePickerListener
import org.hamcrest.Matcher
import java.util.Date

object DatePickerListenerViewActions {

    fun setDate(date: Date): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(DatePickerListener::class.java)
            }

            override fun getDescription(): String {
                return "Set date: $date"
            }

            override fun perform(uiController: UiController?, view: View?) {
                val datePickerAdapter = view as DatePickerListener
                datePickerAdapter.notifyDateChanged(date)
            }
        }
    }
}