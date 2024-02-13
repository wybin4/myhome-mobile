package com.example.myhome.presentation

import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("app:tint")
    fun ImageView.setImageTint(@ColorInt color: Int) {
        setColorFilter(color)
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }


}