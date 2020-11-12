package com.ejooyoung.pdf_reader.base.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.ejooyoung.pdf_reader.viewer.ViewerActivity

object ActivityUtils {

    fun startViewerActivity(activity: Activity, uri: Uri) {
        val intent = Intent(activity, ViewerActivity::class.java).apply {
            data = uri
        }
        activity.startActivity(intent)
    }
}