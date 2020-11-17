package com.ejooyoung.pdf_reader.bookshelf.dialog

import com.ejooyoung.pdf_reader.model.Book

interface LongClickPopupListener {
    fun renameBook(book: Book)
    fun bookMarkBook(book: Book)
    fun deleteBook(book: Book)
}