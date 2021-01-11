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
            core.openPage(pdfDocument, index)
            val originWidth = core.getPageWidthPoint(pdfDocument, index)
            val originHeight = core.getPageHeightPoint(pdfDocument, index)
            val targetWidth = ThumbnailUtils.THUMB_WIDTH
            val targetHeight = ((originHeight / originWidth.toFloat()) * targetWidth).toInt()
            return@fromCallable Bitmap.createBitmap(
                targetWidth,
                targetHeight,
                Bitmap.Config.ARGB_8888
            ).apply {
                core.renderPageBitmap(
                    pdfDocument, this,
                    index, 0, 0, targetWidth, targetHeight
                )
            }
        }
    }
}