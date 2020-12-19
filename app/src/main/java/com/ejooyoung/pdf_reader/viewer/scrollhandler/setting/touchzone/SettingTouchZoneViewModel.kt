package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.app.Application
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingRepository
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone

class SettingTouchZoneViewModel(
    application: Application,
    private val repository: ViewerSettingRepository
) : BaseAndroidViewModel(application) {

    val touchZone = TouchZone.of(100, 100, 10)

    companion object {
        fun newInstance(
            application: Application,
            repository: ViewerSettingRepository
        ): SettingTouchZoneViewModel {
            return SettingTouchZoneViewModel(application, repository)
        }
    }

    fun onTouchZoneWidthChanged(progress: Int) {
        DevLogger.i("widthProgress: $progress")
        touchZone.set(widthProgress = progress)
    }

    fun onTouchZoneHeightChanged(progress: Int) {
        DevLogger.i("heightProgress: $progress")
        touchZone.set(heightProgress = progress)
    }

    fun onTouchZoneMarginTopChanged(progress: Int) {
        DevLogger.i("marginTopProgress: $progress")
        touchZone.set(marginTopProgress = progress)
    }
}