package com.ejooyoung.pdf_reader.util

import android.graphics.Bitmap
import android.net.Uri
import androidx.core.net.toUri
import com.ejooyoung.pdf_reader.MainApplication
import com.ejooyoung.pdf_reader.bookshelf.BookshelfRepository
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.model.Thumbnail
import com.ejooyoung.pdf_reader.repository.ThumbnailRepositoryImpl
import com.shockwave.pdfium.PdfiumCore
import java.io.File
import java.io.FileOutputStream

object ThumbnailUtils {

    private const val PAGE_NUM_TO_MAKE_THUMB = 0

    fun create(application: MainApplication, book: Book): Thumbnail {
        val core = PdfiumCore(application)
        val pdfDocument =
            core.newDocument(application.contentResolver.openFileDescriptor(Uri.parse(book.uri), "r"))
        core.openPage(pdfDocument, PAGE_NUM_TO_MAKE_THUMB)
        val width = core.getPageWidthPoint(pdfDocument, PAGE_NUM_TO_MAKE_THUMB)
        val height = core.getPageHeightPoint(pdfDocument, PAGE_NUM_TO_MAKE_THUMB)
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        core.renderPageBitmap(
            pdfDocument, bmp,
            PAGE_NUM_TO_MAKE_THUMB, 0, 0, width, height
        )
        val dirPath = application.getThumbDir()
        val thumbnail = Thumbnail.valueOf(dirPath, book.guid)
        saveImage(thumbnail.getAbsolutePath(), bmp)
        ThumbnailRepositoryImpl.getInstance(application).insertThumbnails(thumbnail).subscribe()
        core.closeDocument(pdfDocument)
        return thumbnail
    }

    private fun saveImage(absolutePath: String, bmp: Bitmap) {
        val f = File(absolutePath)
        val out = FileOutputStream(f)
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.close()
    }
}