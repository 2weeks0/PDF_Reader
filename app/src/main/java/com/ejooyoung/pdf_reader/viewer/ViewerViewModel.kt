package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.ext.makeToast
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import com.ejooyoung.pdf_reader.base.utils.DevLogger
import com.ejooyoung.pdf_reader.databinding.LayoutScrollHandlerBinding
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.viewer.menu.ContentsActivity
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
        viewerRepository.isBookmarkedPage(book.guid, currentPage.value!!.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    DevLogger.d("isBookmarkedPage: $it")
                    isBookmarkedPage.set(it)
                }
    }

    override fun previousPage(pdfView: PDFView, binding: ViewDataBinding) {
        DevLogger.w("currentPage.value: ${currentPage.value!!}")
        val target = currentPage.value!! - 1
        if (target < 0) {
            return
        }
        pdfView.jumpTo(target, true)
    }

    override fun nextPage(pdfView: PDFView, binding: ViewDataBinding) {
        DevLogger.w("currentPage.value: ${currentPage.value!!}")
        val target = currentPage.value!! + 1
        if (target >= pdfView.pageCount) {
            return
        }
        pdfView.jumpTo(target, true)
    }

    override fun showInfo() {
        DevLogger.i()
    }

    override fun performUndo() {
        DevLogger.i()
    }

    override fun showContents(view: View) {
        DevLogger.i()
        val intent = Intent(view.context, ContentsActivity::class.java).apply {
            putExtra(Const.KEY_BUNDLE_BOOK, book)
        }
        view.context.startActivity(intent)
    }

    override fun addBookmark() {
        viewerRepository.insertBookmark(currentPage.value!!.toLong(), book.guid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isBookmarkedPage.set(true)
                makeToast(R.string.msg_toast_add_bookmark)
            }
    }

    override fun deleteBookmark() {
        viewerRepository.deleteBookmark(currentPage.value!!.toLong(), book.guid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                isBookmarkedPage.set(false)
                makeToast(R.string.msg_toast_del_bookmark)
            }
    }

    override fun showSetting() {
        DevLogger.i()
    }

    override fun onCleared() {
        super.onCleared()
        DevLogger.i()
    }
}