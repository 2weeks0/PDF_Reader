package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import com.ejooyoung.pdf_reader.model.Book

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
            .subscribe()
    }
}