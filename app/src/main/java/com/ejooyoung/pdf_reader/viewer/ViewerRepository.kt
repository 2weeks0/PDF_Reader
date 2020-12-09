package com.ejooyoung.pdf_reader.viewer

import com.ejooyoung.pdf_reader.base.repository.BookmarkRepository
import com.ejooyoung.pdf_reader.database.model.Book
import io.reactivex.rxjava3.core.Completable

interface ViewerRepository: BookmarkRepository {
    fun updateBook(book: Book): Completable
}