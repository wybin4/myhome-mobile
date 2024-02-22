package com.example.myhome.testutils.espresso

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.test.core.app.ApplicationProvider
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withDrawable(
    @DrawableRes drawableRes: Int,
    @ColorRes tintColorRes: Int? = null
) : Matcher<View> = WithDrawable(drawableRes, tintColorRes)

private class WithDrawable(
    private val drawable: Drawable,
    @ColorRes private val tintColorRes: Int?
) : TypeSafeMatcher<View>() {

    constructor(
        @DrawableRes drawableRes: Int,
        @ColorRes tintColorRes: Int?
    ) : this(
        ContextCompat.getDrawable(
            ApplicationProvider.getApplicationContext(),
            drawableRes
        )!!,
        tintColorRes
    )

    override fun describeTo(description: Description) {
        description.appendText("ImageView with the specified drawable res.")
    }

    override fun matchesSafely(item: View): Boolean {
        if (item !is ImageView) return false
        if (tintColorRes != null) {
            drawable.setTintList(
                ColorStateList.valueOf(
                    ContextCompat.getColor(item.context, tintColorRes)
                )
            )
        }
        return item.drawable.toBitmap().sameAs(drawable.toBitmap())
    }
}