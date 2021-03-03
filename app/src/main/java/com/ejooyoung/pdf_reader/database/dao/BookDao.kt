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
    fun selectAllBooks(): Flowable<List<Book>>

    @Query("SELECT count(${Const.DB_BOOK_COLUMN_GUID}) FROM ${Const.DB_BOOK_TABLE}")
    fun selectAllBookCount(): Flowable<Int>

    @Query(
        "SELECT count(${Const.DB_BOOK_COLUMN_GUID})" +
                " FROM ${Const.DB_BOOK_TABLE}" +
                " WHERE ${Const.DB_BOOK_COLUMN_FAVORITE} = 1"
    )
    fun selectFavoriteBooksCount(): Flowable<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(vararg book: Book): Completable

    @Delete
    fun deleteBooks(vararg book: Book): Completable

    @Query(
        "SELECT * FROM ${Const.DB_BOOK_TABLE}" +
                " WHERE ${Const.DB_BOOK_COLUMN_FILE_NAME} = :fileName" +
                " AND ${Const.DB_BOOK_COLUMN_URI} = :uri"
    )
    fun selectBook(fileName: String, uri: String): Maybe<Book?>

    @Update
    fun updateBook(book: Book): Completable

    @Query(
        "SELECT book.guid, book.fileName, book.uri, book.thumbnailGuid, book.lastPage," +
                " book.currentPage, book.readTime, book.favorite," +
                " (SELECT categoryGuid FROM ${Const.DB_CATEGORY_RELATION_TABLE} WHERE categoryGuid = :categoryGuid AND book.guid = bookGuid) AS categoryGuid" +
                " FROM ${Const.DB_BOOK_TABLE}" +
                " WHERE categoryGuid IS NULL"
    )
    fun selectBooksNotInCategory(categoryGuid: String): List<Book>
}