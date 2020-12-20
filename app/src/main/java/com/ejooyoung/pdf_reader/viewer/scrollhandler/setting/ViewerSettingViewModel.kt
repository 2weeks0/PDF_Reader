package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.application.preference.ViewerPreferenceMap
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.SettingTouchZoneActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ViewerSettingViewModel(
    application: Application,
    private val repository: ViewerSettingRepository
) : BaseAndroidViewModel(application), OnClickMenuListener {

    val preferenceMap: MutableLiveData<ViewerPreferenceMap> = MutableLiveData()
    val isTouchZoneActive = ObservableBoolean(true)

    companion object {
        fun newInstance(
            application: Application,
            viewerSettingRepository: ViewerSettingRepository
        ): ViewerSettingViewModel {
            return ViewerSettingViewModel(application, viewerSettingRepository)
        }
    }

    override fun onResume() {
        super.onResume()
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
                isTouchZoneActive.set(it[ViewerPreference.TOUCH_ZONE])
            }
    }

    override fun onClickMenu(view: View, viewerPreference: ViewerPreference) {
        val beforeStatus = preferenceMap.value?.get(viewerPreference)?: viewerPreference.defValue
        val disposable = repository.savePreference(viewerPreference, !beforeStatus)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (viewerPreference == ViewerPreference.TOUCH_ZONE) {
                    isTouchZoneActive.set(!isTouchZoneActive.get())
                }
            }
        compositeDisposable.add(disposable)
    }

    fun startSettingTouchZone(view: View) {
        val intent = Intent(view.context, SettingTouchZoneActivity::class.java)
        view.context.startActivity(intent)
    }
}