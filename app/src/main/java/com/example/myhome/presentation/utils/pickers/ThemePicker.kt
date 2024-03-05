package com.example.myhome.presentation.utils.pickers

import android.content.res.Configuration
import androidx.fragment.app.FragmentActivity

interface ThemePicker {
    fun isDarkTheme(activity: FragmentActivity): Boolean {
        val darkModeFlag = activity.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
    }
}