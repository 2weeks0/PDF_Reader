package com.ejooyoung.pdf_reader.database

import android.content.Context

object DatabaseProvider {

    fun provideBookSource(context: Context): BookDataSource {
        val database = BookDatabase.getInstance(context)
        return database.bookDataSource()
    }
}