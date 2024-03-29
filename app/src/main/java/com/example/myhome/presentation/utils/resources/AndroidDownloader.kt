package com.example.myhome.presentation.utils.resources

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.webkit.URLUtil
import androidx.core.net.toUri

class AndroidDownloader(
    context: Context
) {
    private val downloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    fun downloadFile(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(URLUtil.guessFileName(url, null, null))
//            .addRequestHeader("Authorization", "Bearer <token>")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg")
        return downloadManager.enqueue(request)
    }
}