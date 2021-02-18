package com.ejooyoung.pdf_reader.database.dao

import androidx.room.*
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.base.Const
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface BookDao {

    @Query("SELECT * FROM ${Const.DB_BOOK_TABLE}")
    fun selectAllBooks(): List<Book>

    @Query("SELECT * FROM ${Const.DB_BOOK_TABLE}" +
            " WHERE ${Const.DB_BOOK_COLUMN_FAVORITE} = 1")
    fun selectFavoriteBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(vararg book: Book): Completable

    @Delete
    fun deleteBooks(vararg book: Book): Completable

    @Query("SELECT * FROM ${Const.DB_BOOK_TABLE}" +
            " WHERE ${Const.DB_BOOK_COLUMN_FILE_NAME} = :fileName" +
            " AND ${Const.DB_BOOK_COLUMN_URI} = :uri")
    fun selectBook(fileName: String, uri: String): Maybe<Book?>

    @Update
    fun updateBook(book: Book): Completable
}