package com.example.myhome.presentation.utils.pickers.permissions

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("InlinedApi")
class NotificationPermissionPicker(activity: AppCompatActivity) : PermissionPicker(
    activity,
    Manifest.permission.POST_NOTIFICATIONS,
    PERMISSION_REQUEST_CODE_NOTIFICATION
) {

    companion object {
        private const val PERMISSION_REQUEST_CODE_NOTIFICATION = 101
    }

    fun checkStoragePermission() {
        if (!isPermissionGranted()) {
            requestPermission()
        }
    }
}