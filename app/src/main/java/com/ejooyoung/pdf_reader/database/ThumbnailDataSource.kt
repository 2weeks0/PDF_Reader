package com.ejooyoung.pdf_reader.database

import androidx.room.*
import com.ejooyoung.pdf_reader.model.Thumbnail
import com.ejooyoung.pdf_reader.base.Const
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe


@Dao
interface ThumbnailDataSource {

    @Query("SELECT * FROM ${Const.DB.Thumbnail.TABLE}" +
            " WHERE ${Const.DB.Thumbnail.COLUMN_GUID} = :thumbGuid")
    fun selectThumbnail(thumbGuid: String): Maybe<Thumbnail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertThumbnails(vararg thumbnail: Thumbnail): Completable

    @Delete
    fun deleteThumbnails(vararg thumbnail: Thumbnail): Completable
}