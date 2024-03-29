package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import io.reactivex.rxjava3.core.Completable

interface SettingTouchZoneRepository {
    fun loadTouchZonePreference(touchZone: TouchZone): Completable
    fun saveTouchZonePreference(touchZone: TouchZone): Completable
}