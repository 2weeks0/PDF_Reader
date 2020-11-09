package com.ejooyoung.pdf_reader.bookshelf

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.model.Book
import io.reactivex.rxjava3.disposables.CompositeDisposable

class BookshelfViewModel private constructor(
    application: Application
) : AndroidViewModel(application) {

    private val repository = BookshelfRepository.newInstance()
    private val compositeDisposable = CompositeDisposable()
    val bookList = MutableLiveData(arrayListOf<Book>())

    companion object {
        fun newInstance(application: Application) = BookshelfViewModel(application)
    }

    init {
        loadBookList()
    }

    private fun loadBookList() {

    }
}