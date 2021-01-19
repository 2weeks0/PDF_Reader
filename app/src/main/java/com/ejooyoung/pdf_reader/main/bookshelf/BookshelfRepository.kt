package com.ejooyoung.pdf_reader.main.bookshelf

import com.ejooyoung.pdf_reader.database.model.Book
import io.reactivex.rxjava3.core.Flowable

interface BookshelfRepository {
    fun loadBookList(): Flowable<List<Book>>
}