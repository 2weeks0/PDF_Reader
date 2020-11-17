package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import com.ejooyoung.pdf_reader.base.utils.Logger
import com.ejooyoung.pdf_reader.databinding.LayoutScrollHandlerBinding
import com.ejooyoung.pdf_reader.model.Book
import com.github.barteksc.pdfviewer.PDFView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ViewerViewModel private constructor(
    application: Application,
    private val bookRepository: BookRepository,
    val book: Book
) : AndroidViewModel(application) {

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

    fun previousPage(pdfView: PDFView, binding: ViewDataBinding) {
        val target = pdfView.currentPage - 1
        Logger.d("target: $target, pageCount: ${pdfView.pageCount}")
        if (target < 0) return
        pdfView.jumpTo(target, true)
        currentPage.set(target)
        (binding as LayoutScrollHandlerBinding).seekBar.progress = target
    }

    fun nextPage(pdfView: PDFView, binding: ViewDataBinding) {
        val target = pdfView.currentPage + 1
        Logger.d("target: $target, pageCount: ${pdfView.pageCount}")
        if (target >= pdfView.pageCount) return
        pdfView.jumpTo(target, true)
        currentPage.set(target)
        (binding as LayoutScrollHandlerBinding).seekBar.progress = target
    }

    fun showInfo() {
        Logger.i()
    }

    fun performUndo() {
        Logger.i()
    }

    fun showContents() {
        Logger.i()
    }

    fun showBookmark() {
        Logger.i()
    }

    fun showSetting() {
        Logger.i()
    }

    override fun onCleared() {
        super.onCleared()
        Logger.i()
    }
}