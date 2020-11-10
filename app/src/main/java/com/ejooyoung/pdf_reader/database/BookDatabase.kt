package com.ejooyoung.pdf_reader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.util.Const

@Database(entities = [Book::class], version = Const.DB.VERSION)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDataSource(): BookDataSource

    companion object {
        @Volatile private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                BookDatabase::class.java, Const.DB.FILE_NAME
            ).build()
    }
}