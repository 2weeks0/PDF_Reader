package com.ejooyoung.pdf_reader.base.repository

import android.app.Application
import com.ejooyoung.pdf_reader.database.BookDataSource
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.model.Book

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

    override fun selectAllBooks() =
        bookDataSource.selectAllBooks().onErrorReturnItem(emptyList())!!

    override fun selectBook(fileName: String, uri: String) =
        bookDataSource.selectBook(fileName, uri).onErrorReturn { null }!!

    override fun insertBooks(vararg book: Book) =
        bookDataSource.insertBooks(*book).onErrorComplete()!!

    override fun deleteBooks(vararg book: Book) =
        bookDataSource.deleteBooks(*book).onErrorComplete()!!
}