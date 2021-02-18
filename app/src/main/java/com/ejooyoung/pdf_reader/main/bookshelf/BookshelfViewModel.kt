package com.ejooyoung.pdf_reader.main.bookshelf

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.base.dialog.BookPopupDialog
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.main.bookshelf.listener.OnClickBookListener
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import com.ejooyoung.pdf_reader.viewer.ViewerActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookshelfViewModel private constructor(
    application: Application,
    private val repository: BookshelfRepository
) : BaseAndroidViewModel(application), OnClickBookListener {

    val bookList = MutableLiveData(listOf<Book>())

    companion object {
        fun newInstance(
            application: Application,
            repository: BookshelfRepository
        ): BookshelfViewModel {
            return BookshelfViewModel(application, repository)
        }
    }

    fun loadBookList(currentCategory: CurrentCategory) {
        DevLogger.i()
        DevLogger.d("currentCategory: $currentCategory")
        val disposable = repository.loadBookList(currentCategory)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                DevLogger.i()
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