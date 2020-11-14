package com.ejooyoung.pdf_reader.bookshelf

import android.app.Application
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.model.Book
import io.reactivex.rxjava3.core.Flowable

class BookshelfRepositoryImpl private constructor(
    application: Application
) : BookshelfRepository {

    private var bookRepository: BookRepository = BookRepositoryImpl.getInstance(application)

    companion object {
        fun newInstance(application: Application) = BookshelfRepositoryImpl(application)
    }

    override fun loadBookList(): Flowable<List<Book>> {
        return bookRepository.selectAllBooks()
            .onErrorReturnItem(emptyList())!!
    }
}