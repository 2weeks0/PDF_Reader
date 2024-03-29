package com.ejooyoung.pdf_reader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Thumbnail
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.database.dao.BookDao
import com.ejooyoung.pdf_reader.database.dao.BookmarkDao
import com.ejooyoung.pdf_reader.database.dao.ThumbnailDao
import com.ejooyoung.pdf_reader.database.model.Bookmark

@Database(entities = [Book::class, Thumbnail::class, Bookmark::class], version = Const.DB_VERSION, exportSchema = false)
abstract class DatabaseSource : RoomDatabase() {

    abstract fun thumbnailDao(): ThumbnailDao
    abstract fun bookDao(): BookDao
    abstract fun bookmarkDao(): BookmarkDao

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