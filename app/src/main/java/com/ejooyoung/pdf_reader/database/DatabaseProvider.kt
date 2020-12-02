package com.ejooyoung.pdf_reader.database

import android.content.Context
import com.ejooyoung.pdf_reader.database.dao.BookDao
import com.ejooyoung.pdf_reader.database.dao.ThumbnailDao

object DatabaseProvider {

    fun provideBookSource(context: Context): BookDao {
        val database = DatabaseSource.getInstance(context)
        return database.bookDao()
    }

    fun provideThumbnailSource(context: Context): ThumbnailDao {
        val database = DatabaseSource.getInstance(context)
        return database.thumbnailDao()
    }


}