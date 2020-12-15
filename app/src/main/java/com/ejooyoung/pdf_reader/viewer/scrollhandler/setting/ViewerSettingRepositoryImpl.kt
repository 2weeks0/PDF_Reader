package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.app.Application
import com.ejooyoung.pdf_reader.application.MainApplication
import com.ejooyoung.pdf_reader.application.PreferenceType
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.util.*

@Suppress("UNCHECKED_CAST")
class ViewerSettingRepositoryImpl private constructor(
    private val mainApplication: MainApplication
) : ViewerSettingRepository {

    companion object {
        fun newInstance(application: Application): ViewerSettingRepository {
            return ViewerSettingRepositoryImpl(application as MainApplication)
        }
    }

    override fun savePreference(preferenceType: PreferenceType, value: Boolean): Completable {
        return Completable.fromAction {
            mainApplication.putPreference(preferenceType, value)
        }
    }

    override fun loadAllPreference(): Observable<EnumMap<PreferenceType, Boolean>> {
        val observableList = PreferenceType.values().asSequence()
            .map { loadPreference(it, it.defValue) }
            .toList()
        return Observable.zip(observableList) {
            EnumMap<PreferenceType, Boolean>(PreferenceType::class.java).apply {
                it.forEach { pair -> put((pair as Pair<PreferenceType, Boolean>).first, pair.second) }
            }
        }
    }

    private fun loadPreference(
        preferenceType: PreferenceType,
        defValue: Boolean
    ): Observable<Pair<PreferenceType, Boolean>> {
        return Observable.fromCallable {
            Pair(preferenceType, mainApplication.getPreference(preferenceType, defValue))
        }
    }
}