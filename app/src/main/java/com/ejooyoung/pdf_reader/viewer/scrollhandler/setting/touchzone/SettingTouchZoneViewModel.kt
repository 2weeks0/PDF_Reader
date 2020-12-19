package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.app.Application
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingRepository

class SettingTouchZoneViewModel(
    application: Application,
    private val repository: ViewerSettingRepository
) : BaseAndroidViewModel(application) {

    companion object {
        fun newInstance(
            application: Application,
            repository: ViewerSettingRepository
        ): SettingTouchZoneViewModel {
            return SettingTouchZoneViewModel(application, repository)
        }
    }
}