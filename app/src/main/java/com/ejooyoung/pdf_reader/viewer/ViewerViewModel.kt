package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.findFragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.ext.*
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Bookmark
import com.github.barteksc.pdfviewer.PDFView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ViewerViewModel private constructor(
    application: Application,
    private val viewerRepository: ViewerRepository,
    val book: Book
) : AndroidViewModel(application), ViewerMenuClickListener {

    val visibilityScrollHandler = ObservableBoolean(false)
    val currentPage = MutableLiveData<Int>(book.currentPage)
    val isBookmarkedPage = ObservableBoolean(false)

    companion object {
        fun newInstance(
            application: Application,
            viewerRepository: ViewerRepository,
            book: Book
        ) = ViewerViewModel(application, viewerRepository, book)
    }

    init {
        updateReadTime()
    }

    private fun updateReadTime() {
        book.readTime = DateUtils.getCurrentTimeToDate()
    }

    fun updateBook() {
        book.currentPage = currentPage.value!!
        viewerRepository.updateBook(book)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!
            .subscribe()
    }

    fun updateIsBookmarkedPage() {
        viewerRepository.isBookmarkedPage(book.guid, currentPage.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    DevLogger.d("isBookmarkedPage: $it")
                    isBookmarkedPage.set(it)
                }
    }

    override fun previousPage(pdfView: PDFView) {
        DevLogger.w("currentPage.value: ${currentPage.value!!}")
        val target = currentPage.value!! - 1
        if (target < 0) {
            return
        }
        pdfView.jumpTo(target, true)
    }

    override fun nextPage(pdfView: PDFView) {
        DevLogger.w("currentPage.value: ${currentPage.value!!}")
        val target = currentPage.value!! + 1
        if (target >= pdfView.pageCount) {
            return
        }
        pdfView.jumpTo(target, true)
    }

    override fun showInfo(view: View) {
        DevLogger.i()
    }

    override fun performUndo(view: View) {
        DevLogger.i()
    }

    override fun showContents(view: View) {
        DevLogger.i()
        view.findFragment<ViewerFragment>().startContentsActivity(book)
    }

    override fun addBookmark(view: View) {
        val bookmark = Bookmark.valueOf(
            getApplication<Application>().resources.getString(R.string.txt_unknown_name),
            currentPage.value!!,
            book.guid
        )
        viewerRepository.insertBookmark(bookmark)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isBookmarkedPage.set(true)
                makeSnack(view, R.string.msg_toast_add_bookmark, R.string.btn_change) {
                    view.findFragment<ViewerFragment>().startRenameActivity(
                        R.string.txt_rename_bookmark,
                        bookmark
                    )
                }
            }
    }

    override fun deleteBookmark(view: View) {
        viewerRepository.deleteBookmark(currentPage.value!!, book.guid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isBookmarkedPage.set(false)
                makeToast(R.string.msg_toast_del_bookmark)
            }
    }

    override fun showSetting(view: View) {
        view.findFragment<ViewerFragment>().startViewerSettingActivity()
    }

    override fun onCleared() {
        super.onCleared()
        DevLogger.i()
    }

    fun updateRenamedBookmark(bookmark: Bookmark) {
        viewerRepository.updateBookmark(bookmark)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                makeToast(R.string.msg_success_rename_bookmark)
            }
    }
}