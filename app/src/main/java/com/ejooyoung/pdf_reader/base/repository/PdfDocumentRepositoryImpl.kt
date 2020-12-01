package com.ejooyoung.pdf_reader.base.repository

import com.shockwave.pdfium.PdfDocument

class PdfDocumentRepositoryImpl : PdfDocumentRepository {

    override lateinit var contentsList: List<PdfDocument.Bookmark>

    companion object {
        private var INSTANCE: PdfDocumentRepository? = null

        fun getInstance(): PdfDocumentRepository {
            if (INSTANCE == null) {
                INSTANCE = PdfDocumentRepositoryImpl()
            }
            return INSTANCE!!
        }
    }

    override fun loadContentsList(contentsList: List<PdfDocument.Bookmark>) {
        this.contentsList = contentsList
    }
}