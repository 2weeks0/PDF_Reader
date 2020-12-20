package com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone

import android.app.Application
import android.view.View
import androidx.databinding.ObservableBoolean
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.OnClickMenuListener
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingTouchZoneViewModel(
    application: Application,
    private val repository: SettingTouchZoneRepository
) : BaseAndroidViewModel(application), OnClickMenuListener {

    val touchZone = TouchZone.of()
    val isTouchZoneActive = ObservableBoolean()

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
        val disposable1 = repository.loadPreference()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isTouchZoneActive.set(it)
            }
        val disposable2 = repository.loadTouchZonePreference(touchZone)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.addAll(disposable1, disposable2)
    }

    private fun saveTouchZonePreference(touchZone: TouchZone) {
        val disposable = Completable.concat {
            repository.saveTouchZonePreference(touchZone)
            repository.savePreference(isTouchZoneActive.get())
        }
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

    override fun onClickMenu(view: View, viewerPreference: ViewerPreference) {
        isTouchZoneActive.set(!isTouchZoneActive.get())
        val disposable = repository.savePreference(isTouchZoneActive.get())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(disposable)
    }
}