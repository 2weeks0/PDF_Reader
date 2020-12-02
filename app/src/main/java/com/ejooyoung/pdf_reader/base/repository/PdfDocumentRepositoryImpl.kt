package com.ejooyoung.pdf_reader.base.repository

import com.ejooyoung.pdf_reader.database.model.PdfDocumentBookmark
import com.shockwave.pdfium.PdfDocument

class PdfDocumentRepositoryImpl : PdfDocumentRepository {

    override lateinit var pdfDocumentBookmarkList: List<PdfDocumentBookmark>

    companion object {
        private var INSTANCE: PdfDocumentRepository? = null

        fun getInstance(): PdfDocumentRepository {
            if (INSTANCE == null) {
                INSTANCE = PdfDocumentRepositoryImpl()
            }
            return INSTANCE!!
        }
    }

    override fun savePdfDocumentBookmarkList(itemList: List<PdfDocument.Bookmark>) {
        pdfDocumentBookmarkList = itemList.asSequence()
            .map {
                PdfDocumentBookmark(
                    title = it.title,
                    pageIdx = it.pageIdx
                )
            }
            .toList()
    }
}