package com.ejooyoung.pdf_reader.viewer.menu.bookmark

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.base.repository.BookmarkRepository
import com.ejooyoung.pdf_reader.base.repository.PdfDocumentRepositoryImpl
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Contents
import com.ejooyoung.pdf_reader.viewer.menu.model.ContentsType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class BookmarkListViewModel(
        application: Application,
        private val bookmarkRepository: BookmarkRepository,
        private val book: Book
) : AndroidViewModel(application) {

    companion object {
        fun newInstance(
                application: Application,
                bookmarkRepository: BookmarkRepository,
                book: Book
        ): BookmarkListViewModel {
            return BookmarkListViewModel(application, bookmarkRepository, book)
        }
    }

    val contentsItemList: MutableLiveData<List<Contents>> = MutableLiveData()
    val visibilityOfEmptyView: ObservableBoolean = ObservableBoolean(false)

    init {
        loadContentsList()
    }

    private fun loadContentsList() {
        bookmarkRepository.selectAllBookmarks(book.guid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    contentsItemList.value = it
                    visibilityOfEmptyView.set(it.isEmpty())
                }
    }
}