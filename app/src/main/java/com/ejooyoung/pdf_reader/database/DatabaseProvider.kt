package com.ejooyoung.pdf_reader.database

import android.content.Context

object DatabaseProvider {

    fun providePdfSource(context: Context): PdfDao {
        val database = PdfDatabase.getInstance(context)
        return database.pdfDao()
    }
}