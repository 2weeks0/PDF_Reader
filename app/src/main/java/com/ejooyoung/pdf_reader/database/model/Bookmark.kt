package com.ejooyoung.pdf_reader.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import java.util.*

@Entity(tableName = Const.DB_BOOKMARK_TABLE)
data class Bookmark(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_GUID)
    private val guid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_TITLE)
    private val title: String = "",
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_PAGE_INDEX)
    private val pageIdx: Long,
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_BOOK_GUID)
    val bookGuid: String,
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_BOOKMARKED_TIME)
    val bookmarkedTime: String = DateUtils.getCurrentTimeToDate()
): Contents {

    companion object {
        fun valueOf(pageIdx: Long, bookGuid: String): Bookmark {
            return Bookmark(pageIdx = pageIdx, bookGuid = bookGuid)
        }
    }
    override fun getGuid(): String {
        return guid
    }

    override fun getTitle(): String {
        return title
    }

    override fun getPageIdx(): Long {
        return pageIdx
    }
}