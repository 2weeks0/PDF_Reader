package com.ejooyoung.pdf_reader.base.mvvm

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseAndroidViewModel(
    application: Application
) : AndroidViewModel(application) {

    val visibilityOfProgressBar = ObservableBoolean(false)
    protected val compositeDisposable = CompositeDisposable()
    protected var loadDisposable: Disposable? = null

    open fun onResume() {

    }

    open fun onPause() {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}