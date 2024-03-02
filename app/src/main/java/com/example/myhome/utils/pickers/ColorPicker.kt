package com.example.myhome.utils.pickers

import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

interface ColorPicker {
    fun getColor(context: FragmentActivity, color: Int): Int {
        return ContextCompat.getColor(context, color)
    }

}
