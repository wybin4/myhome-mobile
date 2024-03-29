package com.example.myhome.presentation.utils.resources

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import androidx.core.net.toUri

class AndroidDownloader(
    context: Context
) {
    private val downloadManager =
        context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    fun downloadFile(url: String, name: String): Long {
        val request = DownloadManager.Request(Uri.parse(url))
            .setMimeType("application/pdf")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(URLUtil.guessFileName(url, null, null))
//            .addRequestHeader("Authorization", "Bearer <token>")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
        return downloadManager.enqueue(request)
    }

    fun downloadImage(url: String, name: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(URLUtil.guessFileName(url, null, null))
//            .addRequestHeader("Authorization", "Bearer <token>")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name)
        return downloadManager.enqueue(request)
    }
}