package com.ejooyoung.pdf_reader.base.utils

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import com.ejooyoung.pdf_reader.application.MainApplication
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Thumbnail
import com.ejooyoung.pdf_reader.base.repository.ThumbnailRepositoryImpl
import com.shockwave.pdfium.PdfiumCore
import java.io.File
import java.io.FileOutputStream

object ThumbnailUtils {

    private const val PAGE_NUM_TO_MAKE_THUMB = 0
    const val THUMB_WIDTH = 300

    fun createMainThumbnail(application: MainApplication, book: Book): Thumbnail {
        val bmp = getThumbnail(application, book, PAGE_NUM_TO_MAKE_THUMB)
        val dirPath = application.getThumbDir()
        val thumbnail = Thumbnail.valueOf(dirPath, book.guid)
        saveImage(thumbnail.getAbsolutePath(), bmp)
        ThumbnailRepositoryImpl.getInstance(application).insertThumbnails(thumbnail).subscribe()
        return thumbnail
    }

    fun getThumbnail(application: Application, book: Book, index: Int): Bitmap {
        val core = PdfiumCore(application)
        val pdfDocument =
            core.newDocument(application.contentResolver.openFileDescriptor(Uri.parse(book.uriString), "r"))
        core.openPage(pdfDocument, index)
        val originWidth = core.getPageWidthPoint(pdfDocument, index)
        val originHeight = core.getPageHeightPoint(pdfDocument, index)
        val targetWidth = THUMB_WIDTH
        val targetHeight = ((originHeight / originWidth.toFloat()) * targetWidth).toInt()

        val bmp = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
        core.renderPageBitmap(
            pdfDocument, bmp,
            index, 0, 0, targetWidth, targetHeight
        )
        core.closeDocument(pdfDocument)
        return bmp
    }

    private fun saveImage(absolutePath: String, bmp: Bitmap) {
        val f = File(absolutePath)
        val out = FileOutputStream(f)
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.close()
    }
}