package com.ejooyoung.pdf_reader.application

import android.app.Application
import android.preference.PreferenceManager
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

    fun putPreference(preferenceType: PreferenceType, value: Boolean) {
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putBoolean(preferenceType.toString(), value)
            .apply()
    }

    fun getPreference(preferenceType: PreferenceType, defValue: Boolean): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(preferenceType.toString(), defValue)
    }
}