package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import com.ejooyoung.pdf_reader.base.utils.Logger
import com.ejooyoung.pdf_reader.model.Book
import com.github.barteksc.pdfviewer.PDFView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ViewerViewModel private constructor(
    application: Application,
    private val bookRepository: BookRepository,
    private val book: Book
) : AndroidViewModel(application) {

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

    fun previousPage(pdfView: PDFView) {
        val target = pdfView.currentPage - 1
        Logger.d("target: $target, pageCount: ${pdfView.pageCount}")
        if (target < 0) return
        pdfView.jumpTo(target, true)
    }

    fun nextPage(pdfView: PDFView) {
        val target = pdfView.currentPage + 1
        Logger.d("target: $target, pageCount: ${pdfView.pageCount}")
        if (target >= pdfView.pageCount) return
        pdfView.jumpTo(target, true)
    }

    override fun onCleared() {
        super.onCleared()
        Logger.i()
    }
}