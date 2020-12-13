package com.ejooyoung.pdf_reader.bookshelf

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.base.dialog.BookPopupDialog
import com.ejooyoung.pdf_reader.bookshelf.listener.OnClickBookListener
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.viewer.ViewerActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class BookshelfViewModel private constructor(
    application: Application,
    private val repository: BookshelfRepository
) : AndroidViewModel(application), OnClickBookListener {

    private val compositeDisposable = CompositeDisposable()
    val bookList = MutableLiveData(listOf<Book>())

    companion object {
        fun newInstance(
            application: Application,
            repository: BookshelfRepository
        ) = BookshelfViewModel(application, repository)
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

    override fun onLongClickBook(view: View, book: Book): Boolean {
        BookPopupDialog.newInstance(book, BookRepositoryImpl.getInstance(getApplication()))
            .show((view.context as AppCompatActivity).supportFragmentManager, null)
        return true
    }
}