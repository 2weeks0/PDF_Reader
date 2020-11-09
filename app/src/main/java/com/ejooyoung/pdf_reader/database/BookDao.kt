package com.ejooyoung.pdf_reader.database

import androidx.room.*
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.util.Const


@Dao
interface BookDao {

    @Query("SELECT * FROM ${Const.DB.TABLE_NAME_BOOK}")
    fun selectAllBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(vararg book: Book)

    @Delete
    fun deleteBook(book: Book)
}