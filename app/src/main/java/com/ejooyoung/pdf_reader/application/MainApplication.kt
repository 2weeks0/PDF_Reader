package com.ejooyoung.pdf_reader.application

import android.app.Application
import android.preference.PreferenceManager
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import com.ejooyoung.pdf_reader.application.preference.TouchZonePreference
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

    fun getViewerPreference(viewerPreference: ViewerPreference): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(viewerPreference.toString(), viewerPreference.defValue)
    }

    private fun getTouchZonePreference(touchZonePreference: TouchZonePreference): Int {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getInt(touchZonePreference.toString(), touchZonePreference.defValue)
    }

    fun getTouchZonePreference(touchZone: TouchZone) {
        touchZone.set(
            getTouchZonePreference(TouchZonePreference.WIDTH_PROGRESS),
            getTouchZonePreference(TouchZonePreference.HEIGHT_PROGRESS),
            getTouchZonePreference(TouchZonePreference.MARGIN_PROGRESS),
            if (getViewerPreference(ViewerPreference.TOUCH_ZONE)) 1 else 0,
            getTouchZonePreference(TouchZonePreference.IS_HORIZONTAL),
            getTouchZonePreference(TouchZonePreference.IS_LEFT_ACTION_PREVIOUS_PAGE),
            getTouchZonePreference(TouchZonePreference.IS_RIGHT_ACTION_NEXT_PAGE)
        )
    }

    fun putTouchZonePreference(touchZone: TouchZone) {
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putInt(TouchZonePreference.WIDTH_PROGRESS.toString(), touchZone.widthProgress.get())
            .putInt(TouchZonePreference.HEIGHT_PROGRESS.toString(), touchZone.heightProgress.get())
            .putInt(TouchZonePreference.MARGIN_PROGRESS.toString(), touchZone.marginProgress.get())
            .putBoolean(ViewerPreference.TOUCH_ZONE.toString(), touchZone.isActive.get())
            .putInt(TouchZonePreference.IS_HORIZONTAL.toString(), if (touchZone.isHorizontal.get()) 1 else 0)
            .putInt(
                TouchZonePreference.IS_LEFT_ACTION_PREVIOUS_PAGE.toString(),
                if (touchZone.isLeftActionPreviousPage.get()) 1 else 0)
            .putInt(
                TouchZonePreference.IS_RIGHT_ACTION_NEXT_PAGE.toString(),
                if (touchZone.isRightActionNextPage.get()) 1 else 0)
            .apply()
    }

    fun putPreference(key: String, value: String) {
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putString(key, value)
            .apply()
    }

    fun getPreference(key: String, defValue: String): String {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getString(key, defValue)?: defValue
    }

    fun putPreference(key: String, value: Int) {
        PreferenceManager.getDefaultSharedPreferences(this)
            .edit()
            .putInt(key, value)
            .apply()
    }

    fun getPreference(key: String, defValue: Int): Int {
        return PreferenceManager.getDefaultSharedPreferences(this)
            .getInt(key, defValue)
    }
}