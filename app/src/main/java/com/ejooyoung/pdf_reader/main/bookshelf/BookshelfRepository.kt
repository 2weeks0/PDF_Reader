package com.ejooyoung.pdf_reader.main.bookshelf

import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.main.model.CurrentCategory
import io.reactivex.rxjava3.core.Flowable

interface BookshelfRepository {
    fun loadOriginBookList(): Flowable<List<Book>>
    fun loadBookList(bookList: List<Book>, currentCategory: CurrentCategory): Flowable<List<Book>>
}