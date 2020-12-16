package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.application.PreferenceType
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class ViewerSettingViewModel(
    application: Application,
    private val repository: ViewerSettingRepository
) : BaseAndroidViewModel(application), OnClickMenuListener {

    val preferenceMap = MutableLiveData(EnumMap<PreferenceType, Boolean>(PreferenceType::class.java))

    companion object {
        fun newInstance(
            application: Application,
            viewerSettingRepository: ViewerSettingRepository
        ): ViewerSettingViewModel {
            return ViewerSettingViewModel(application, viewerSettingRepository)
        }
    }

    init {
        loadPreference()
    }

    private fun loadPreference() {
        loadDisposable?.dispose()
        visibilityOfProgressBar.set(true)
        loadDisposable = repository.loadAllPreference()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                visibilityOfProgressBar.set(false)
                preferenceMap.value = it
            }
    }

    override fun onClickMenu(view: View, preferenceType: PreferenceType) {
        val beforeStatus = preferenceMap.value?.get(preferenceType)?: preferenceType.defValue
        val disposable = repository.savePreference(preferenceType, !beforeStatus)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {

            }
        compositeDisposable.add(disposable)
    }
}