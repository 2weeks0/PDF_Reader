package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.content.Context
import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Observable

interface GridViewerRepository {
    fun getThumbnail(context: Context, index: Int): Observable<Bitmap>
}