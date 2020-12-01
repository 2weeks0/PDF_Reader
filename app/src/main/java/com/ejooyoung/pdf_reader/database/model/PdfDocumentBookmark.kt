package com.ejooyoung.pdf_reader.database.model

import java.util.*

// PDf 파일 내 목차
data class PdfDocumentBookmark(
    private val guid: String = UUID.randomUUID().toString(),
    private val title: String,
    private val pageIdx: Long
) : Contents {

    override fun getGuid(): String {
        return guid
    }

    override fun getTitle(): String {
        return title
    }

    override fun getPageIdx(): Long {
        return pageIdx
    }
}