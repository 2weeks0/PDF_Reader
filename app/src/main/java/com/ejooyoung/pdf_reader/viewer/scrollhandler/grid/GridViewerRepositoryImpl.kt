package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.res.ResourcesCompat
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.ext.withBorder
import com.ejooyoung.pdf_reader.base.utils.ThumbnailUtils
import com.ejooyoung.pdf_reader.database.model.Book
import com.shockwave.pdfium.PdfiumCore
import io.reactivex.rxjava3.core.Observable

class GridViewerRepositoryImpl private constructor(
    private val application: Application,
    book: Book
): GridViewerRepository {

    private val core = PdfiumCore(application)
    private val pdfDocument = core.newDocument(
        application.contentResolver.openFileDescriptor(Uri.parse(book.uriString), "r"))

    companion object {
        fun newInstance(application: Application, book: Book): GridViewerRepository {
            return GridViewerRepositoryImpl(application, book)
        }
    }

    override fun getBookPageCount(): Int {
        return core.getPageCount(pdfDocument)
    }

    override fun getThumbnail(index: Int): Observable<Bitmap> {
        return Observable.fromCallable {
            ThumbnailUtils.getThumbnail(core, pdfDocument, index).withBorder(
                2, ResourcesCompat.getColor(application.resources, R.color.bg_thumb_border, null))
        }
    }
}