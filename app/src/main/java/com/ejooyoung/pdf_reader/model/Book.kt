package com.ejooyoung.pdf_reader.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ejooyoung.pdf_reader.util.Const
import java.util.*

@Entity(tableName = Const.DB.Book.TABLE)
data class Book(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Const.DB.Book.COLUMN_GUID)
    val guid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = Const.DB.Book.COLUMN_FILE_NAME)
    val fileName: String,
    @ColumnInfo(name = Const.DB.Book.COLUMN_URI)
    val uri: String,
    @ColumnInfo(name = Const.DB.Book.COLUMN_THUMBNAIL_GUID)
    var thumbnailGuid: String
) {
    companion object {
        fun valueOf(fileName: String, uri: String, thumbnailGuid: String) =
            Book(fileName = fileName, uri = uri, thumbnailGuid = thumbnailGuid)
    }
}