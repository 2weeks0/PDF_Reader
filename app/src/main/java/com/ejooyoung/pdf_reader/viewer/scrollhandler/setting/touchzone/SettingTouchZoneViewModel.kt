package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.app.Application
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingTouchZoneViewModel(
    application: Application,
    private val repository: SettingTouchZoneRepository
) : BaseAndroidViewModel(application) {

    val touchZone = TouchZone.of()

    companion object {
        fun newInstance(
            application: Application,
            repository: SettingTouchZoneRepository
        ): SettingTouchZoneViewModel {
            return SettingTouchZoneViewModel(application, repository)
        }
    }

    init {
        loadTouchZonePreference()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveTouchZonePreference(touchZone)
    }

    private fun loadTouchZonePreference() {
        loadDisposable?.dispose()
        loadDisposable = repository.loadTouchZonePreference(touchZone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
    }

    private fun saveTouchZonePreference(touchZone: TouchZone) {
        val disposable = repository.saveTouchZonePreference(touchZone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {}
        compositeDisposable.add(disposable)
    }

    fun onTouchZoneWidthChanged(progress: Int) {
        DevLogger.i("widthProgress: $progress")
        touchZone.set(widthProgress = progress)
    }

    fun onTouchZoneHeightChanged(progress: Int) {
        DevLogger.i("heightProgress: $progress")
        touchZone.set(heightProgress = progress)
    }

    fun onTouchZoneMarginChanged(progress: Int) {
        DevLogger.i("marginProgress: $progress")
        touchZone.set(marginProgress = progress)
    }
}