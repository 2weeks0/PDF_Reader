package com.ejooyoung.pdf_reader.base.repository

import android.app.Application
import com.ejooyoung.pdf_reader.database.BookDataSource
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.model.Book
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class BookRepositoryImpl private constructor(
    private val bookDataSource: BookDataSource
) : BookRepository {

    companion object {
        private var INSTANCE: BookRepository? = null

        fun getInstance(application: Application): BookRepository {
            if (INSTANCE == null) {
                INSTANCE = BookRepositoryImpl(DatabaseProvider.provideBookSource(application))
            }
            return INSTANCE!!
        }
    }

    override fun selectAllBooks(): Flowable<List<Book>> {
        return bookDataSource.selectAllBooks()
            .onErrorReturnItem(emptyList())!!
    }

    override fun selectBook(fileName: String, uri: String): Maybe<Book?> {
        return bookDataSource.selectBook(fileName, uri)
            .onErrorReturn { null }
    }

    override fun insertBooks(vararg book: Book): Completable {
        return bookDataSource.insertBooks(*book)
            .onErrorComplete()!!
    }

    override fun deleteBooks(vararg book: Book): Completable {
        return bookDataSource.deleteBooks(*book)
            .onErrorComplete()!!
    }

    override fun updateBook(book: Book): Completable {
        return bookDataSource.updateBook(book)
            .onErrorComplete()!!
    }
}