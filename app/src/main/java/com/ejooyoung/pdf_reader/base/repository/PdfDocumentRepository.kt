package com.ejooyoung.pdf_reader.base.repository

import com.ejooyoung.pdf_reader.database.model.PdfDocumentBookmark
import com.shockwave.pdfium.PdfDocument

interface PdfDocumentRepository {
    var pdfDocumentBookmarkList: List<PdfDocumentBookmark>
    fun savePdfDocumentBookmarkList(itemList: List<PdfDocument.Bookmark>)
}