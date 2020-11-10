package com.ejooyoung.pdf_reader.repository

import com.ejooyoung.pdf_reader.model.Book
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface BookRepository {
    fun selectAllBooks(): Observable<List<Book>>
    fun insertBooks(vararg book: Book): Completable
    fun deleteBooks(vararg book: Book): Completable
}