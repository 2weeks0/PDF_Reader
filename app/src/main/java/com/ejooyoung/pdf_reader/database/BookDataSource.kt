package com.ejooyoung.pdf_reader.database

import androidx.room.*
import com.ejooyoung.pdf_reader.model.Book
import com.ejooyoung.pdf_reader.base.Const
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface BookDataSource {

    @Query("SELECT * FROM ${Const.DB.Book.TABLE}")
    fun selectAllBooks(): Flowable<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(vararg book: Book): Completable

    @Delete
    fun deleteBooks(vararg book: Book): Completable

    @Query("SELECT * FROM ${Const.DB.Book.TABLE}" +
            " WHERE ${Const.DB.Book.COLUMN_FILE_NAME} = :fileName" +
            " AND ${Const.DB.Book.COLUMN_URI} = :uri")
    fun selectBook(fileName: String, uri: String): Maybe<Book>
}