package com.ejooyoung.pdf_reader.application

import android.app.Application
import android.preference.PreferenceManager
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZonePreference
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

    fun getTouchZonePreference(touchZonePreference: TouchZonePreference): Int {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getInt(touchZonePreference.toString(), touchZonePreference.defValue)
    }

    fun putTouchZonePreference(touchZone: TouchZone) {
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putInt(TouchZonePreference.WIDTH_PROGRESS.toString(), touchZone.widthProgress.get())
            .putInt(TouchZonePreference.HEIGHT_PROGRESS.toString(), touchZone.heightProgress.get())
            .putInt(TouchZonePreference.MARGIN_PROGRESS.toString(), touchZone.marginProgress.get())
            .apply()
    }
}