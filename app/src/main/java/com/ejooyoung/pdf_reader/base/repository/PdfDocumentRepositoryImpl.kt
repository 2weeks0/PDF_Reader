package com.ejooyoung.pdf_reader.base.repository

import com.shockwave.pdfium.PdfDocument

class PdfDocumentRepositoryImpl : PdfDocumentRepository {

    override val contentsList: MutableList<PdfDocument.Bookmark> = mutableListOf()

    companion object {
        private var INSTANCE: PdfDocumentRepository? = null

        fun getInstance(): PdfDocumentRepository {
            if (INSTANCE == null) {
                INSTANCE = PdfDocumentRepositoryImpl()
            }
            return INSTANCE!!
        }
    }

    override fun saveContentsList(contentsList: List<PdfDocument.Bookmark>) {
        this.contentsList.addAll(contentsList)
    }

    override fun clearContentsList() {
        this.contentsList.clear()
    }
}