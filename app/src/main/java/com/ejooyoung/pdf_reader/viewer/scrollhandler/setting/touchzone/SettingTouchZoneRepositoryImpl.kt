package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.app.Application
import com.ejooyoung.pdf_reader.application.MainApplication
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZonePreference
import io.reactivex.rxjava3.core.Completable

class SettingTouchZoneRepositoryImpl private constructor(
    private val mainApplication: MainApplication
): SettingTouchZoneRepository {

    companion object {
        fun newInstance(application: Application): SettingTouchZoneRepository {
            return SettingTouchZoneRepositoryImpl(application as MainApplication)
        }
    }

    override fun loadTouchZonePreference(touchZone: TouchZone): Completable {
        return Completable.fromAction {
            touchZone.set(
                mainApplication.getTouchZonePreference(TouchZonePreference.WIDTH_PROGRESS),
                mainApplication.getTouchZonePreference(TouchZonePreference.HEIGHT_PROGRESS),
                mainApplication.getTouchZonePreference(TouchZonePreference.MARGIN_PROGRESS)
            )
        }
    }

    override fun saveTouchZonePreference(touchZone: TouchZone): Completable {
        return Completable.fromAction {
            mainApplication.putTouchZonePreference(touchZone)
        }.onErrorComplete()
    }
}