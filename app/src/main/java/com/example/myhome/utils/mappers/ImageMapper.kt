package com.example.myhome.utils.mappers

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ImageMapper @Inject constructor() {
    fun mapImageToDomain(image: Bitmap?): String {
        if (image == null || image.width <= 0 || image.height <= 0) {
            return ""
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        byteArrayOutputStream.close()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}