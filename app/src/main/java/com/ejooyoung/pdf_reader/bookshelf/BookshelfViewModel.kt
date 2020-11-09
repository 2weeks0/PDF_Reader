package com.ejooyoung.pdf_reader.bookshelf

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.model.PDF
import io.reactivex.rxjava3.disposables.CompositeDisposable

class BookshelfViewModel private constructor(
    application: Application
) : AndroidViewModel(application) {

    private val repository = BookshelfRepository.newInstance()
    private val compositeDisposable = CompositeDisposable()
    val pdfList = MutableLiveData(arrayListOf<PDF>())

    companion object {
        fun newInstance(application: Application) = BookshelfViewModel(application)
    }

    init {
        loadPdfList()
    }

    private fun loadPdfList() {

    }
}