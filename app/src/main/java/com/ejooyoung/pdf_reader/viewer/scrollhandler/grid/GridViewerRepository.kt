package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.graphics.Bitmap
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfiumCore
import io.reactivex.rxjava3.core.Observable

interface GridViewerRepository {
    fun getThumbnail(
        core: PdfiumCore,
        pdfDocument: PdfDocument,
        index: Int
    ): Observable<Bitmap>
}