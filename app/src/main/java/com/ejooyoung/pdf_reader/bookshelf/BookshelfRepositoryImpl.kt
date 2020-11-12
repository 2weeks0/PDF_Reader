package com.ejooyoung.pdf_reader.bookshelf

import android.app.Application
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl

class BookshelfRepositoryImpl private constructor(
    application: Application
) : BookshelfRepository {

    private var bookRepository: BookRepository = BookRepositoryImpl.getInstance(application)

    companion object {
        fun newInstance(application: Application) = BookshelfRepositoryImpl(application)
    }

    override fun loadBookList() = bookRepository.selectAllBooks()
}