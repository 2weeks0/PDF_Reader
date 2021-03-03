package com.ejooyoung.pdf_reader.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ejooyoung.pdf_reader.base.Const
import java.util.*

@Entity(tableName = Const.DB_CATEGORY_RELATION_TABLE)
data class CategoryRelation(
    @PrimaryKey
    @ColumnInfo(name = Const.DB_CATEGORY_RELATION_COLUMN_GUID)
    val guid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = Const.DB_CATEGORY_RELATION_COLUMN_BOOK_GUID)
    val bookGuid: String,
    @ColumnInfo(name = Const.DB_CATEGORY_RELATION_COLUMN_CATEGORY_GUID)
    val categoryGuid: String
) {
    companion object {
        fun valueOf(bookGuid: String, categoryGuid: String): CategoryRelation {
            return CategoryRelation(bookGuid = bookGuid, categoryGuid = categoryGuid)
        }
    }
}