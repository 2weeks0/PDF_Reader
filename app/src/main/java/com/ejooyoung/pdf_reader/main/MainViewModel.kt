package com.ejooyoung.pdf_reader.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.MainApplication
import com.ejooyoung.pdf_reader.model.PDF
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainRepository.newInstance()
    private val compositeDisposable = CompositeDisposable()
    val pdfList = MutableLiveData(arrayListOf<PDF>())

    init {
        loadPdfList()
    }

    fun loadPdfList() {
        val disposable = repository.loadPdfList(getApplication())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                pdfList.value = it
            }
        compositeDisposable.add(disposable)
    }
}