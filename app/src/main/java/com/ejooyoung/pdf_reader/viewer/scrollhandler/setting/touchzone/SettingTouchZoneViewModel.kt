package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.app.Application
import android.view.View
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.OnClickMenuListener
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingTouchZoneViewModel(
    application: Application,
    private val repository: SettingTouchZoneRepository
) : BaseAndroidViewModel(application), OnClickMenuListener {

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

    private fun loadTouchZonePreference() {
        val disposable = repository.loadTouchZonePreference(touchZone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    private fun saveTouchZonePreference(touchZone: TouchZone) {
        val disposable = repository.saveTouchZonePreference(touchZone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun onTouchZoneWidthChanged(progress: Int) {
        DevLogger.i("widthProgress: $progress")
        touchZone.set(widthProgress = progress)
        saveTouchZonePreference(touchZone)
    }

    fun onTouchZoneHeightChanged(progress: Int) {
        DevLogger.i("heightProgress: $progress")
        touchZone.set(heightProgress = progress)
        saveTouchZonePreference(touchZone)
    }

    fun onTouchZoneMarginChanged(progress: Int) {
        DevLogger.i("marginProgress: $progress")
        touchZone.set(marginProgress = progress)
        saveTouchZonePreference(touchZone)
    }

    override fun onClickMenu(view: View, viewerPreference: ViewerPreference) {
        touchZone.set(isActive = if (touchZone.isActive.get()) 0 else 1)
        saveTouchZonePreference(touchZone)
    }

    fun setTouchZoneHorizon(boolean: Boolean) {
        touchZone.isHorizontal.set(boolean)
        saveTouchZonePreference(touchZone)
    }
}