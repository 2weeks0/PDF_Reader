package com.ejooyoung.pdf_reader.application

import android.app.Application
import android.preference.PreferenceManager
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import java.io.File

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        makeThumbDirIfNotExist()
    }

    private fun makeThumbDirIfNotExist() {
        val folder = File(getThumbDir())
        if (!folder.exists()) folder.mkdirs()
    }

    fun getThumbDir() = getExternalFilesDir(null)!!.path + "/thumb"

    fun putPreference(viewerPreference: ViewerPreference, value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putBoolean(viewerPreference.toString(), value)
            .apply()
    }

    fun getPreference(viewerPreference: ViewerPreference, defValue: Boolean): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(viewerPreference.toString(), defValue)
    }
}