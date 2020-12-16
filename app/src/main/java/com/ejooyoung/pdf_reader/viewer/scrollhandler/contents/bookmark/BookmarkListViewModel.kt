package com.ejooyoung.pdf_reader.viewer.scrollhandler.contents.bookmark

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.mvvm.BaseAndroidViewModel
import com.ejooyoung.pdf_reader.base.repository.BookmarkRepository
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Contents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class BookmarkListViewModel(
        application: Application,
        private val bookmarkRepository: BookmarkRepository,
        private val book: Book
) : BaseAndroidViewModel(application) {

    val contentsItemList: MutableLiveData<List<Contents>> = MutableLiveData()
    val visibilityOfEmptyView: ObservableBoolean = ObservableBoolean(false)

    companion object {
        fun newInstance(
                application: Application,
                bookmarkRepository: BookmarkRepository,
                book: Book
        ): BookmarkListViewModel {
            return BookmarkListViewModel(application, bookmarkRepository, book)
        }
    }

    init {
        loadContentsList()
    }

    private fun loadContentsList() {
        loadDisposable?.dispose()
        visibilityOfProgressBar.set(true)
        loadDisposable = bookmarkRepository.selectAllBookmarks(book.guid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    visibilityOfProgressBar.set(false)
                    contentsItemList.value = it
                    visibilityOfEmptyView.set(it.isEmpty())
                }
    }
}