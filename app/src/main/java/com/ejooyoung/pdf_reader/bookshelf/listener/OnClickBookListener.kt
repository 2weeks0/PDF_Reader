package com.ejooyoung.pdf_reader.bookshelf.listener

import android.view.View
import com.ejooyoung.pdf_reader.database.model.Book

interface OnClickBookListener {
    fun onClickBook(view: View, book: Book)
    fun onLongClickBook(view: View, book: Book): Boolean
}