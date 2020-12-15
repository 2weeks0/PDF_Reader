package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.app.Application
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import com.ejooyoung.pdf_reader.application.PreferenceType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class ViewerSettingViewModel(
    application: Application,
    private val repository: ViewerSettingRepository
) : AndroidViewModel(application), OnClickMenuListener {

    val preferenceMap = EnumMap<PreferenceType, ObservableBoolean>(PreferenceType::class.java)
    private val compositeDisposable = CompositeDisposable()

    companion object {
        fun newInstance(
            application: Application,
            viewerSettingRepository: ViewerSettingRepository
        ): ViewerSettingViewModel {
            return ViewerSettingViewModel(application, viewerSettingRepository)
        }
    }

    init {
        initPreferenceMap()
        loadPreference()
    }

    private fun initPreferenceMap() {
        PreferenceType.values().asSequence()
            .forEach { preferenceMap[it] = ObservableBoolean() }
    }

    private fun loadPreference() {
        val disposable = repository.loadAllPreference()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.keys.asSequence()
                    .forEach { key -> preferenceMap[key]?.set(it[key]?: key.defValue) }
            }
        compositeDisposable.add(disposable)
    }

    override fun onClickMenu(view: View, preferenceType: PreferenceType) {
        val beforeStatus = preferenceMap[preferenceType]?.get()?: preferenceType.defValue
        val disposable = repository.savePreference(preferenceType, !beforeStatus)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            }
        compositeDisposable.add(disposable)
    }
}