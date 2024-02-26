package com.example.myhome.utils.mappers

import android.graphics.Bitmap
import android.util.Base64
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImageMapperTest {

    private lateinit var imageMapper: ImageMapper

    @Before
    fun setUp() {
        imageMapper = ImageMapper()
    }

    @Test
    fun mapImageToDomain_returnsEmptyString_whenImageIsNull() {
        val result = imageMapper.mapImageToDomain(null)
        assertEquals("", result)
    }

    @Test
    fun mapImageToDomain_returnsBase64EncodedString() {
        val testBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)

        val expectedBase64String = convertBitmapToBase64(testBitmap)
        val result = imageMapper.mapImageToDomain(testBitmap)

        assertEquals(expectedBase64String, result)
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}
