package com.ejooyoung.pdf_reader.database.dao

import androidx.room.*
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.database.model.Bookmark
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM ${Const.DB_BOOKMARK_TABLE}" +
            " WHERE ${Const.DB_BOOKMARK_COLUMN_BOOK_GUID} = :bookGuid" +
            " ORDER BY ${Const.DB_BOOKMARK_COLUMN_PAGE_INDEX}")
    fun selectAllBookmarks(bookGuid: String): Flowable<List<Bookmark>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmarks(vararg bookmark: Bookmark): Completable

    @Query("DELETE FROM ${Const.DB_BOOKMARK_TABLE}" +
            " WHERE ${Const.DB_BOOKMARK_COLUMN_PAGE_INDEX} = :pageIdx" +
            " AND ${Const.DB_BOOKMARK_COLUMN_BOOK_GUID} = :bookGuid")
    fun deleteBookmark(pageIdx: Int, bookGuid: String): Completable

    @Query("DELETE FROM ${Const.DB_BOOKMARK_TABLE} WHERE ${Const.DB_BOOKMARK_COLUMN_BOOK_GUID} = :bookGuid")
    fun deleteBookmark(bookGuid: String): Completable

    @Update
    fun updateBookmark(bookmark: Bookmark): Completable

    @Query("SELECT CASE WHEN count(${Const.DB_BOOKMARK_COLUMN_GUID}) > 0 THEN 1 ELSE 0 END" +
            " FROM ${Const.DB_BOOKMARK_TABLE}" +
            " WHERE ${Const.DB_BOOKMARK_COLUMN_BOOK_GUID} = :bookGuid" +
            " AND ${Const.DB_BOOKMARK_COLUMN_PAGE_INDEX} = :pageIdx")
    fun selectIsBookmarkedPage(bookGuid: String, pageIdx: Int): Flowable<Boolean>
}