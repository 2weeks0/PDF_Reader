package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Observable

interface GridViewerRepository {
    fun getBookPageCount(): Int
    fun getThumbnail(index: Int): Observable<Bitmap>
}