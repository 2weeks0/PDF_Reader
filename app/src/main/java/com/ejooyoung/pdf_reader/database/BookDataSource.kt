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
}