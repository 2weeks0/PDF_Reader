package com.ejooyoung.pdf_reader.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ejooyoung.pdf_reader.base.Const
import java.util.*

@Entity(tableName = Const.DB_CATEGORY_TABLE)
data class Category(
    @PrimaryKey
    @ColumnInfo(name = Const.DB_CATEGORY_COLUMN_GUID)
    val guid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = Const.DB_CATEGORY_COLUMN_NAME)
    val name: String
) {

    companion object {
        fun valueOf(name: String): Category {
            return Category(name = name)
        }
    }
}