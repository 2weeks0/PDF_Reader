package com.ejooyoung.pdf_reader.base.repository

import com.ejooyoung.pdf_reader.database.model.Book
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

interface BookRepository {
    fun selectAllBooks(): Flowable<List<Book>>
    fun selectFavoriteBooks(): Flowable<List<Book>>
    fun selectBook(fileName: String, uri: String): Maybe<Book?>
    fun insertBooks(vararg book: Book): Completable
    fun deleteBooks(vararg book: Book): Completable
    fun updateBook(book: Book): Completable
}