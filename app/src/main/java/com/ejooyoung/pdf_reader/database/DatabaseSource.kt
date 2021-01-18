package com.ejooyoung.pdf_reader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.database.dao.*
import com.ejooyoung.pdf_reader.database.model.*

@Database(entities = [Book::class, Thumbnail::class, Bookmark::class, Category::class, CategoryRelation::class], version = Const.DB_VERSION, exportSchema = false)
abstract class DatabaseSource : RoomDatabase() {

    abstract fun thumbnailDao(): ThumbnailDao
    abstract fun bookDao(): BookDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun categoryDao(): CategoryDao
    abstract fun categoryRelationDao(): CategoryRelationDao

    companion object {
        @Volatile private var INSTANCE: DatabaseSource? = null

        fun getInstance(context: Context): DatabaseSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DatabaseSource::class.java, Const.DB_FILE_NAME
            ).build()
    }
}