package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.graphics.Bitmap
import com.ejooyoung.pdf_reader.base.utils.ThumbnailUtils
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfiumCore
import io.reactivex.rxjava3.core.Observable

class GridViewerRepositoryImpl private constructor(): GridViewerRepository {

    companion object {
        fun newInstance(): GridViewerRepository {
            return GridViewerRepositoryImpl()
        }
    }

    override fun getThumbnail(
        core: PdfiumCore,
        pdfDocument: PdfDocument,
        index: Int
    ): Observable<Bitmap> {
        return Observable.fromCallable {
            ThumbnailUtils.getThumbnail(core, pdfDocument, index)
        }
    }
}