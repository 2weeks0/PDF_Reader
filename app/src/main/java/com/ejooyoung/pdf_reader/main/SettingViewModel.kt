package com.ejooyoung.pdf_reader.main

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.util.Const

class SettingViewModel private constructor(
    application: Application
) : AndroidViewModel(application) {

    companion object {
        fun newInstance(application: Application) = SettingViewModel(application)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.layAdd -> openFileManager(view.context as Activity)
        }
    }

    private fun openFileManager(activity: Activity) {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }

        activity.startActivityForResult(intent, Const.Request.OPEN_PDF)
    }
}