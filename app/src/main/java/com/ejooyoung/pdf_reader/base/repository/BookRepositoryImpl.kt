package com.ejooyoung.pdf_reader.base.repository

import android.content.Context
import com.ejooyoung.pdf_reader.database.dao.BookDao
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.model.Book
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

class BookRepositoryImpl private constructor(
    private val bookDao: BookDao
) : BookRepository {

    companion object {
        private var INSTANCE: BookRepository? = null

        fun getInstance(context: Context): BookRepository {
            if (INSTANCE == null) {
                INSTANCE = BookRepositoryImpl(DatabaseProvider.provideBookSource(context))
            }
            return INSTANCE!!
        }
    }

    override fun selectAllBooks(): List<Book> {
        return bookDao.selectAllBooks()
    }

    override fun selectFavoriteBooks(): List<Book> {
        return bookDao.selectFavoriteBooks()
    }

    override fun selectBook(fileName: String, uri: String): Maybe<Book?> {
        return bookDao.selectBook(fileName, uri)
            .onErrorReturn { null }
    }

    override fun insertBooks(vararg book: Book): Completable {
        return bookDao.insertBooks(*book)
            .onErrorComplete()
    }

    override fun deleteBooks(vararg book: Book): Completable {
        return bookDao.deleteBooks(*book)
            .onErrorComplete()
    }

    override fun updateBook(book: Book): Completable {
        return bookDao.updateBook(book)
            .onErrorComplete()
    }
}