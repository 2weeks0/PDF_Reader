package com.ejooyoung.pdf_reader.base.repository

import com.shockwave.pdfium.PdfDocument

interface PdfDocumentRepository {
    val contentsList: MutableList<PdfDocument.Bookmark>
    fun saveContentsList(contentsList: List<PdfDocument.Bookmark>)
    fun clearContentsList()
}