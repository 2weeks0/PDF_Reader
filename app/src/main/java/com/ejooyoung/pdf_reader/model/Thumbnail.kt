package com.ejooyoung.pdf_reader.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ejooyoung.pdf_reader.util.Const
import java.util.*

@Entity(tableName = Const.DB.Thumbnail.TABLE)
data class Thumbnail(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Const.DB.Thumbnail.COLUMN_GUID)
    val guid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = Const.DB.Thumbnail.COLUMN_FILE_NAME)
    val fileName: String,
    @ColumnInfo(name = Const.DB.Thumbnail.COLUMN_ABSOLUTE_PATH)
    val absolutePath: String,
    @ColumnInfo(name = Const.DB.Thumbnail.COLUMN_BOOK_GUID)
    val bookGuid: String
)