package com.ejooyoung.pdf_reader.database.model

interface Contents {
    fun getGuid(): String
    fun getTitle(): String
    fun getPageIdx(): Long
}