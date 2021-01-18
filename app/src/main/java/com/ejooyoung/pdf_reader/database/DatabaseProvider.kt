package com.ejooyoung.pdf_reader.database

import android.content.Context
import com.ejooyoung.pdf_reader.database.dao.*

object DatabaseProvider {

    fun provideBookSource(context: Context): BookDao {
        val database = DatabaseSource.getInstance(context)
        return database.bookDao()
    }

    fun provideThumbnailSource(context: Context): ThumbnailDao {
        val database = DatabaseSource.getInstance(context)
        return database.thumbnailDao()
    }

    fun provideBookmarkSource(context: Context): BookmarkDao {
        val database = DatabaseSource.getInstance(context)
        return database.bookmarkDao()
    }

    fun provideCategorySource(context: Context): CategoryDao {
        val database = DatabaseSource.getInstance(context)
        return database.categoryDao()
    }

    fun provideCategoryRelationSource(context: Context): CategoryRelationDao {
        val database = DatabaseSource.getInstance(context)
        return database.categoryRelationDao()
    }
}