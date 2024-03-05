package com.example.myhome.presentation.features.appeal

import android.content.Context
import android.content.res.ColorStateList
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.myhome.R

class AppealPicker(
    private val context: Context,
    private val button: LinearLayout,
    private val icon: LinearLayout,
    private val parentOnClick: () -> Unit
) {

    init {
        button.setOnClickListener{ onClick() }
    }

    private fun setButtonColor(button: LinearLayout, color: Int) {
        val colorVal = ContextCompat.getColor(context, color)
        val colorStateList = ColorStateList.valueOf(colorVal)
        button.backgroundTintList = colorStateList
    }

    private fun setButtonClickedColor() {
        setButtonColor(button, R.color.primary_light_clicked)
    }

     fun setButtonUnclickedColor() {
        setButtonColor(button, R.color.primary_light)
    }

    fun setButtonErrorColor() {
        setButtonColor(button, R.color.red_light)
    }

    fun setIconErrorColor() {
        setButtonColor(icon, R.color.red)
    }

    fun setIconColor() {
        setButtonColor(icon, R.color.primary)
    }

    fun onClick() {
        parentOnClick()
        setButtonClickedColor()
    }
}
