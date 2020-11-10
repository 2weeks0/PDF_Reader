package com.ejooyoung.pdf_reader.database

import androidx.room.*
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.util.Const


@Dao
interface BookDataSource {

    @Query("SELECT * FROM ${Const.DB.Book.TABLE}")
    fun selectAllBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(vararg book: Book)

    @Delete
    fun deleteBooks(vararg book: Book)

    @Query("SELECT count(${Const.DB.Book.COLUMN_GUID}) FROM ${Const.DB.Book.TABLE}" +
            " WHERE ${Const.DB.Book.COLUMN_FILE_NAME} = :fileName" +
            " AND ${Const.DB.Book.COLUMN_URI} = :uri")
    fun containsBook(fileName: String, uri: String): Int
}