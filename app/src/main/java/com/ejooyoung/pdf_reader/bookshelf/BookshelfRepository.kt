package com.ejooyoung.pdf_reader.bookshelf

import com.ejooyoung.pdf_reader.model.Book
import io.reactivex.rxjava3.core.Flowable

interface BookshelfRepository {
    fun loadBookList(): Flowable<List<Book>>
}