package com.ejooyoung.pdf_reader.repository

import com.ejooyoung.pdf_reader.database.BookDataSource
import com.ejooyoung.pdf_reader.model.Book
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

class BookRepositoryImpl private constructor(
    private val bookDataSource: BookDataSource
) : BookRepository {

    companion object {
        private var INSTANCE: BookRepository? = null

        fun getInstance(bookDataSource: BookDataSource): BookRepository {
            if (INSTANCE == null) {
                INSTANCE = BookRepositoryImpl(bookDataSource)
            }
            return INSTANCE!!
        }
    }

    override fun selectAllBooks() = Observable.fromCallable {
        bookDataSource.selectAllBooks()
    }.onErrorReturnItem(emptyList())!!

    override fun insertBooks(vararg book: Book) = Completable.fromAction {
        bookDataSource.insertBooks(*book)
    }.onErrorComplete()!!

    override fun deleteBooks(vararg book: Book) = Completable.fromAction {
        bookDataSource.deleteBooks(*book)
    }.onErrorComplete()!!
}