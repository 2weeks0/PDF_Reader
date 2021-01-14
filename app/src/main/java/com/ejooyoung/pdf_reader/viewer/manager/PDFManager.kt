package com.ejooyoung.pdf_reader.viewer.manager

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.ejooyoung.pdf_reader.database.model.Book
import com.shockwave.pdfium.PdfDocument
import com.shockwave.pdfium.PdfiumCore

class PDFManager private constructor(
    context: Context
) {

    private val pdfiumCore = PdfiumCore(context)
    private var pdfDocument: PdfDocument? = null
    private val loadThumbFlags = hashSetOf<Int>()
    private val thumbCached = hashMapOf<Int, Bitmap>()
    var previewIndex: Int = 0

    companion object {
        private var INSTANCE: PDFManager? = null

        fun getInstance(context: Context): PDFManager {
            if (INSTANCE == null) {
                INSTANCE = PDFManager(context)
            }
            return INSTANCE!!
        }
    }

    fun startLoad(index: Int) {
        loadThumbFlags.add(index)
        previewIndex = index
    }

    fun isStartLoad(index: Int): Boolean {
        return loadThumbFlags.contains(index)
    }

    fun putThumbCached(index: Int, bitmap: Bitmap) {
        thumbCached[index] = bitmap
    }

    fun getThumbCached(index: Int): Bitmap? {
        previewIndex = index
        return thumbCached[index]
    }

    fun openPdfDocument(application: Application, book: Book) {
        closePdfDocument()
        pdfDocument = pdfiumCore.newDocument(
            application.contentResolver.openFileDescriptor(Uri.parse(book.uriString), "r")
        )
    }

    fun closePdfDocument() {
        if (pdfDocument != null) {
            pdfiumCore.closeDocument(pdfDocument)
            pdfDocument = null
            loadThumbFlags.clear()
            thumbCached.clear()
        }
    }

    fun getBounds(index: Int): Array<Int> {
        var originWidth = pdfiumCore.getPageWidthPoint(pdfDocument, index)
        if (originWidth == 0) {
            pdfiumCore.openPage(pdfDocument, index)
            originWidth = pdfiumCore.getPageWidthPoint(pdfDocument, index)
        }
        val originHeight = pdfiumCore.getPageHeightPoint(pdfDocument, index)
        return arrayOf(originWidth, originHeight)
    }

    fun renderBitmap(bitmap: Bitmap, index: Int, targetWidth: Int, targetHeight: Int) {
        pdfiumCore.renderPageBitmap(
            pdfDocument!!, bitmap,
            index, 0, 0, targetWidth, targetHeight
        )
    }
}