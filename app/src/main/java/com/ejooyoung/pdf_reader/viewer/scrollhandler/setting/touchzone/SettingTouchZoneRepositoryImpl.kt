package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.app.Application
import com.ejooyoung.pdf_reader.application.MainApplication
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZonePreference
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

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

    override fun loadPreference(): Observable<Boolean> {
        return Observable.fromCallable {
            mainApplication.getPreference(ViewerPreference.TOUCH_ZONE, ViewerPreference.TOUCH_ZONE.defValue)
        }.onErrorReturnItem(ViewerPreference.TOUCH_ZONE.defValue)
    }

    override fun savePreference(value: Boolean): Completable {
        return Completable.fromAction {
            mainApplication.putPreference(ViewerPreference.TOUCH_ZONE, value)
        }.onErrorComplete()
    }
}