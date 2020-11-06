package com.ejooyoung.pdf_reader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ejooyoung.pdf_reader.model.MyPDF
import com.ejooyoung.pdf_reader.util.Const

@Database(entities = [MyPDF::class], version = Const.DB.VERSION)
abstract class PdfDatabase : RoomDatabase() {

    abstract fun pdfDao(): PdfDao

    companion object {
        @Volatile private var INSTANCE: PdfDatabase? = null

        fun getInstance(context: Context): PdfDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PdfDatabase::class.java, "pdf_reader.db"
            ).build()
    }
}