package com.ejooyoung.pdf_reader.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ejooyoung.pdf_reader.util.Const
import java.util.*

@Entity(tableName = Const.DB.TABLE_NAME_BOOK)
data class Book(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "guid")
    val guid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "file_name")
    val fileName: String,
    @ColumnInfo(name = "absolute_path")
    val absolutePath: String,
    @ColumnInfo(name = "thumbnail_path")
    val thumbnailPath: String)