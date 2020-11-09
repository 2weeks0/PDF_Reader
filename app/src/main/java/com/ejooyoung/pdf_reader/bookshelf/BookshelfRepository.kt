package com.ejooyoung.pdf_reader.bookshelf

import android.os.Environment
import android.util.Log
import com.ejooyoung.pdf_reader.MainApplication
import com.ejooyoung.pdf_reader.util.ThumbnailUtils
import io.reactivex.rxjava3.core.Observable
import java.io.File
import java.lang.Exception

class BookshelfRepository {

    companion object {
        const val PDF_PATTERN = ".pdf"
        const val PAGE_NUM_TO_MAKE_THUMB = 0
        fun newInstance() = BookshelfRepository()
    }


}