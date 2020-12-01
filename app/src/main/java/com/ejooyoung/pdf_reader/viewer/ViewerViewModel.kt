package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import android.content.Intent
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.repository.BookRepository
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
    private val bookRepository: BookRepository,
    val book: Book
) : AndroidViewModel(application), ViewerMenuClickListener {

    val visibilityScrollHandler = ObservableBoolean(false)
    val currentPage = ObservableInt(book.currentPage)

    companion object {
        fun newInstance(
            application: Application,
            bookRepository: BookRepository,
            book: Book
        ) = ViewerViewModel(application, bookRepository, book)
    }

    init {
        updateReadTime()
    }

    private fun updateReadTime() {
        book.readTime = DateUtils.getCurrentTimeToDate()
    }

    fun updateBook() {
        bookRepository.updateBook(book)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())!!
            .subscribe()
    }

    override fun previousPage(pdfView: PDFView, binding: ViewDataBinding) {
        val target = pdfView.currentPage - 1
        DevLogger.d("target: $target, pageCount: ${pdfView.pageCount}")
        if (target < 0) return
        pdfView.jumpTo(target, true)
        currentPage.set(target)
        (binding as LayoutScrollHandlerBinding).seekBar.progress = target
    }

    override fun nextPage(pdfView: PDFView, binding: ViewDataBinding) {
        val target = pdfView.currentPage + 1
        DevLogger.d("target: $target, pageCount: ${pdfView.pageCount}")
        if (target >= pdfView.pageCount) return
        pdfView.jumpTo(target, true)
        currentPage.set(target)
        (binding as LayoutScrollHandlerBinding).seekBar.progress = target
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

    override fun showBookmark() {
        DevLogger.i()
    }

    override fun showSetting() {
        DevLogger.i()
    }

    override fun onCleared() {
        super.onCleared()
        DevLogger.i()
    }
}