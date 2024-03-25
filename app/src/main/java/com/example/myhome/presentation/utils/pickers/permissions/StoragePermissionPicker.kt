package com.example.myhome.presentation.utils.pickers.permissions

import android.Manifest
import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity

@SuppressLint("InlinedApi")
class StoragePermissionPicker(activity: FragmentActivity): PermissionPicker(
    activity,
    Manifest.permission.READ_MEDIA_IMAGES,
    STORAGE_PERMISSION_REQUEST_CODE
) {
    companion object {
        const val STORAGE_PERMISSION_REQUEST_CODE = 100
    }

    fun checkStoragePermission(onPermissionGranted: () -> Unit) {
        if (isPermissionGranted()) {
            onPermissionGranted()
        } else {
            requestPermission()
        }
    }

}