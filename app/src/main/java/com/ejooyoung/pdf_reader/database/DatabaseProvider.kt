package com.ejooyoung.pdf_reader.database

import android.content.Context

object DatabaseProvider {

    fun providePdfSource(context: Context): BookDao {
        val database = BookDatabase.getInstance(context)
        return database.bookDao()
    }
}