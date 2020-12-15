package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import com.ejooyoung.pdf_reader.application.PreferenceType
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.util.*

interface ViewerSettingRepository {
    fun savePreference(preferenceType: PreferenceType, value: Boolean): Completable
    fun loadAllPreference(): Observable<EnumMap<PreferenceType, Boolean>>
}