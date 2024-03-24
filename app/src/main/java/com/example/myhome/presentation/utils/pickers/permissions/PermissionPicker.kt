package com.example.myhome.presentation.utils.pickers.permissions

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

abstract class PermissionPicker(
    protected val activity: FragmentActivity,
    private val permission: String,
    private val requestCode: Int
) {
    protected open fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    protected open fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            requestCode
        )
    }
}