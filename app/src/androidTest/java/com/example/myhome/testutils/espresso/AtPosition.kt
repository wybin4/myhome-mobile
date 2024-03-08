package com.example.myhome.testutils.espresso

import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun atPosition(
    position: Int,
    matcher: Matcher<View>
): Matcher<View> = AtPosition(position, matcher)

private class AtPosition(
    private val position: Int,
    private val matcher: Matcher<View>,
) : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description) {
        description.appendText("recyclerView with the specified ID and at position: $position")
    }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is RecyclerView) return false
        val viewHolder = item.findViewHolderForAdapterPosition(position)
            ?: return false
        return matcher.matches(viewHolder.itemView)
    }

}

fun atPositionInLinearLayout(
    position: Int,
    matcher: Matcher<View>
): Matcher<View> = AtPositionInLinearLayout(position, matcher)

private class AtPositionInLinearLayout(
    private val position: Int,
    private val matcher: Matcher<View>,
) : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description) {
        description.appendText("LinearLayout with the specified ID and at position: $position")
    }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is LinearLayout) return false
        val childView = item.getChildAt(position)
            ?: return false
        return matcher.matches(childView)
    }

}
