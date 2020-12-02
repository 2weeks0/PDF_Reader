package com.ejooyoung.pdf_reader.database.dao

import androidx.room.*
import com.ejooyoung.pdf_reader.database.model.Thumbnail
import com.ejooyoung.pdf_reader.base.Const
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe


@Dao
interface ThumbnailDao {

    @Query("SELECT * FROM ${Const.DB_THUMBNAIL_TABLE}" +
            " WHERE ${Const.DB_THUMBNAIL_COLUMN_GUID} = :thumbGuid")
    fun selectThumbnail(thumbGuid: String): Maybe<Thumbnail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertThumbnails(vararg thumbnail: Thumbnail): Completable

    @Delete
    fun deleteThumbnails(vararg thumbnail: Thumbnail): Completable
}