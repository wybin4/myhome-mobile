package com.example.myhome.presentation.utils.pickers

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri

import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import com.example.myhome.presentation.utils.pickers.permissions.StorageReadPermissionPicker

class ImagePicker(
    private val fragment: Fragment,
    private val forActivityResult: (image: Bitmap) -> Unit
) {
    private val permissionPicker = StorageReadPermissionPicker(fragment.requireActivity())

    private val takePictureLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        this::handleActivityResult
    )

    fun checkStoragePermission() {
        permissionPicker.checkStoragePermission(
            onPermissionGranted = this::openImagePicker
        )
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        takePictureLauncher.launch(intent)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun publicForActivityResult(bitmap: Bitmap) {
        forActivityResult(bitmap)
    }

    private fun handleActivityResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            imageUri?.let { uri ->
                val imageStream = fragment.requireActivity().contentResolver.openInputStream(uri)
                val imageBitmap = BitmapFactory.decodeStream(imageStream)
                forActivityResult(imageBitmap)
            }
        }
    }
}
