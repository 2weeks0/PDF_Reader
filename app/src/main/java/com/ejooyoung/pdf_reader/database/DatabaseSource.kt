package com.ejooyoung.pdf_reader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.model.Thumbnail
import com.ejooyoung.pdf_reader.base.Const

@Database(entities = [Book::class, Thumbnail::class], version = Const.DB.VERSION, exportSchema = false)
abstract class DatabaseSource : RoomDatabase() {

    abstract fun thumbnailDataSource(): ThumbnailDataSource
    abstract fun bookDataSource(): BookDataSource

    companion object {
        @Volatile private var INSTANCE: DatabaseSource? = null

        fun getInstance(context: Context): DatabaseSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DatabaseSource::class.java, Const.DB.FILE_NAME
            ).build()
    }
}