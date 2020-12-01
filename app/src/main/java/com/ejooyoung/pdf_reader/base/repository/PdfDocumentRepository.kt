package com.ejooyoung.pdf_reader.base.repository

import com.shockwave.pdfium.PdfDocument

interface PdfDocumentRepository {
    var contentsList: List<PdfDocument.Bookmark>
    fun loadContentsList(contentsList: List<PdfDocument.Bookmark>)
}