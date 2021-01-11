package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.app.Application
import android.graphics.Bitmap
import com.ejooyoung.pdf_reader.database.model.Book
import io.reactivex.rxjava3.core.Observable

interface GridViewerRepository {
    fun getThumbnail(application: Application, book: Book, index: Int): Observable<Bitmap>
}