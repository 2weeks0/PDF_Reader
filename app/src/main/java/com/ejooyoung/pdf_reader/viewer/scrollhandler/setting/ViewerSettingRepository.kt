package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.util.*

interface ViewerSettingRepository {
    fun savePreference(viewerPreference: ViewerPreference, value: Boolean): Completable
    fun loadAllPreference(): Observable<EnumMap<ViewerPreference, Boolean>>
}