package com.ejooyoung.pdf_reader.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ejooyoung.pdf_reader.base.Const
import java.util.*

@Entity(tableName = Const.DB.Thumbnail.TABLE)
data class Thumbnail(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Const.DB.Thumbnail.COLUMN_GUID)
    val guid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = Const.DB.Thumbnail.COLUMN_DIR_PATH)
    val dirPath: String,
    @ColumnInfo(name = Const.DB.Thumbnail.COLUMN_BOOK_GUID)
    val bookGuid: String
) {
    companion object {
        fun valueOf(dirPath: String, bookGuid: String) =
            Thumbnail(dirPath = dirPath, bookGuid = bookGuid)
    }

    fun getAbsolutePath() = "$dirPath/$guid.png"
}