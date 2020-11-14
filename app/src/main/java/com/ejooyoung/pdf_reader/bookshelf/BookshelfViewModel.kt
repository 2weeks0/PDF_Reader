package com.ejooyoung.pdf_reader.bookshelf

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.bookshelf.listener.OnClickBookListener
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.viewer.ViewerActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class BookshelfViewModel private constructor(
    application: Application
) : AndroidViewModel(application), OnClickBookListener {

    private val repository = BookshelfRepositoryImpl.newInstance(application)
    private val compositeDisposable = CompositeDisposable()
    val bookList = MutableLiveData(listOf<Book>())

    companion object {
        fun newInstance(application: Application) = BookshelfViewModel(application)
    }

    init {
        loadBookList()
    }

    private fun loadBookList() {
        val disposable = repository.loadBookList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                bookList.value = it
            }
        compositeDisposable.add(disposable)
    }

    override fun onClickBook(view: View, book: Book) {
        Intent(view.context, ViewerActivity::class.java).let {
            it.putExtra(Const.KEY_BUNDLE_BOOK, book)
            view.context.startActivity(it)
        }
    }
}