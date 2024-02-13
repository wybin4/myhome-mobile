package com.example.myhome.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class ImagePicker(
    private val fragment: Fragment,
    private val forActivityResult: (image: Bitmap) -> Unit
) {
    private val PERMISSION_REQUEST_CODE = 100
    private val takePictureLauncher = fragment.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        this::handleActivityResult
    )

    fun checkStoragePermission() {
        if (isStoragePermissionGranted()) {
            openImagePicker()
        } else {
            requestStoragePermission()
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            fragment.requireActivity(),
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        takePictureLauncher.launch(intent)
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
