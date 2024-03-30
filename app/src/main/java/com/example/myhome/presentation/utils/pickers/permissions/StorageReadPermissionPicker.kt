package com.example.myhome.presentation.utils.pickers.permissions

import android.Manifest
import androidx.fragment.app.FragmentActivity

class StorageReadPermissionPicker(activity: FragmentActivity): PermissionPicker(
    activity,
    Manifest.permission.READ_EXTERNAL_STORAGE,
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