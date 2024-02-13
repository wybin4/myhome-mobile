package com.example.myhome.presentation

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageMapper @Inject constructor() {
    fun mapImageToDomain(image: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}