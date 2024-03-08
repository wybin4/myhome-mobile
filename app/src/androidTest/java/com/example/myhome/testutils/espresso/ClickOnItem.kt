package com.example.myhome.testutils.espresso

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

fun clickOnItemInLinearLayout(parentPosition: Int, childPosition: Int, linearLayoutId: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isAssignableFrom(RecyclerView::class.java)
        }

        override fun getDescription(): String {
            return "click on item inside LinearLayout with id $linearLayoutId at position $parentPosition"
        }

        override fun perform(uiController: UiController?, view: View) {
            val recyclerView = view as RecyclerView
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(parentPosition)
                ?: throw IllegalStateException("No view holder found at position $parentPosition")

            val linearLayout = viewHolder.itemView.findViewById<LinearLayout>(linearLayoutId)
                ?: throw IllegalStateException("LinearLayout with id $linearLayoutId not found")

            val itemToClick = linearLayout.getChildAt(childPosition)
                ?: throw IllegalStateException("No child view found at position $childPosition in LinearLayout with id $linearLayoutId")
            Log.e("itemToClick", itemToClick.toString())
            itemToClick.performClick()
        }
    }
}

