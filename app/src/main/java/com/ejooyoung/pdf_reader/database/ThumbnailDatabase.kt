package com.ejooyoung.pdf_reader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ejooyoung.pdf_reader.model.Thumbnail
import com.ejooyoung.pdf_reader.util.Const

@Database(entities = [Thumbnail::class], version = Const.DB.VERSION, exportSchema = true)
abstract class ThumbnailDatabase : RoomDatabase() {

    abstract fun thumbnailDataSource(): ThumbnailDataSource

    companion object {
        @Volatile private var INSTANCE: ThumbnailDatabase? = null

        fun getInstance(context: Context): ThumbnailDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ThumbnailDatabase::class.java, Const.DB.FILE_NAME
            ).build()
    }
}