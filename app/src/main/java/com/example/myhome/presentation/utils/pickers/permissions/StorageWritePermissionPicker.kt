package com.example.myhome.presentation.utils.pickers.permissions

import android.Manifest
import androidx.fragment.app.FragmentActivity

class StoragePermissionPicker(activity: FragmentActivity) : PermissionPicker(
    activity,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    STORAGE_PERMISSION_REQUEST_CODE
) {
    companion object {
        const val STORAGE_PERMISSION_REQUEST_CODE = 101
    }

    fun checkStoragePermission(onPermissionGranted: () -> Unit) {
        if (isPermissionGranted()) {
            onPermissionGranted()
        } else {
            requestPermission()
        }
    }
}