package com.ejooyoung.pdf_reader.database

import androidx.room.*
import com.ejooyoung.pdf_reader.model.Thumbnail
import com.ejooyoung.pdf_reader.util.Const


@Dao
interface ThumbnailDataSource {

    @Query("SELECT * FROM ${Const.DB.Thumbnail.TABLE} WHERE ${Const.DB.Thumbnail.COLUMN_GUID} = :thumbGuid")
    fun selectThumbnail(thumbGuid: String): List<Thumbnail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertThumbnails(vararg thumbnail: Thumbnail)

    @Delete
    fun deleteThumbnails(vararg thumbnail: Thumbnail)
}