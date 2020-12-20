package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.app.Application
import com.ejooyoung.pdf_reader.application.MainApplication
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.application.preference.ViewerPreferenceMap
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Suppress("UNCHECKED_CAST")
class ViewerSettingRepositoryImpl private constructor(
    private val mainApplication: MainApplication
) : ViewerSettingRepository {

    companion object {
        fun newInstance(application: Application): ViewerSettingRepository {
            return ViewerSettingRepositoryImpl(application as MainApplication)
        }
    }

    override fun savePreference(viewerPreference: ViewerPreference, value: Boolean): Completable {
        return Completable.fromAction {
            mainApplication.putPreference(viewerPreference, value)
        }
    }

    override fun loadAllPreference(): Observable<ViewerPreferenceMap> {
        val observableList = ViewerPreference.values().asSequence()
            .map { loadPreference(it) }
            .toList()
        return Observable.zip(observableList) {
            ViewerPreferenceMap.newInstance().apply {
                it.forEach { pair -> put((pair as Pair<ViewerPreference, Boolean>).first, pair.second) }
            }
        }
    }

    private fun loadPreference(viewerPreference: ViewerPreference)
            : Observable<Pair<ViewerPreference, Boolean>> {
        return Observable.fromCallable {
            Pair(viewerPreference, mainApplication.getViewerPreference(viewerPreference))
        }
    }
}