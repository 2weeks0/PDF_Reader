package com.ejooyoung.pdf_reader.bookshelf.listener

import android.view.View
import com.ejooyoung.pdf_reader.model.Book

interface OnClickBookListener {
    fun onClickBook(view: View, book: Book)
}