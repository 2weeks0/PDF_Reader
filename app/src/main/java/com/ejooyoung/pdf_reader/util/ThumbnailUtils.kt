package com.ejooyoung.pdf_reader.util

import android.graphics.Bitmap
import androidx.core.net.toUri
import com.ejooyoung.pdf_reader.MainApplication
import com.ejooyoung.pdf_reader.bookshelf.BookshelfRepository
import com.shockwave.pdfium.PdfiumCore
import java.io.File
import java.io.FileOutputStream

object ThumbnailUtils {

    fun makeThumbnailIfNotExist(application: MainApplication, file: File): Boolean {
        if (File(application.getThumbPath(file.name)).exists()) {
            return false
        }

        val core = PdfiumCore(application)
        val pdfDocument =
            core.newDocument(application.contentResolver.openFileDescriptor(file.toUri(), "r"))
        core.openPage(pdfDocument, BookshelfRepository.PAGE_NUM_TO_MAKE_THUMB)
        val width = core.getPageWidthPoint(pdfDocument, BookshelfRepository.PAGE_NUM_TO_MAKE_THUMB)
        val height = core.getPageHeightPoint(pdfDocument, BookshelfRepository.PAGE_NUM_TO_MAKE_THUMB)
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        core.renderPageBitmap(
            pdfDocument, bmp,
            BookshelfRepository.PAGE_NUM_TO_MAKE_THUMB, 0, 0, width, height
        )
        saveImage(application, file, bmp)
        core.closeDocument(pdfDocument)
        return true
    }

    private fun saveImage(application: MainApplication, file: File, bmp: Bitmap) {
        val f = File(application.getThumbDir(), file.name.replace("pdf", "png"))
        val out = FileOutputStream(f)
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
        out.close()
    }
}