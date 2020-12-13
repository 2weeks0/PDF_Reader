package com.ejooyoung.pdf_reader.database.model

import android.content.res.Resources
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import com.ejooyoung.pdf_reader.rename.Renamable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = Const.DB_BOOKMARK_TABLE)
data class Bookmark(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_GUID)
    private val guid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_TITLE)
    private var title: String,
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_PAGE_INDEX)
    private val pageIdx: Int,
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_BOOK_GUID)
    val bookGuid: String,
    @ColumnInfo(name = Const.DB_BOOKMARK_COLUMN_BOOKMARKED_TIME)
    val bookmarkedTime: String = DateUtils.getCurrentTimeToDate()
): Contents, Renamable {

    companion object {
        fun valueOf(title: String, pageIdx: Int, bookGuid: String): Bookmark {
            return Bookmark(
                title = title,
                pageIdx = pageIdx,
                bookGuid = bookGuid
            )
        }
    }
    override fun getGuid(): String {
        return guid
    }

    override fun getTitle(): String {
        return title
    }

    override fun getPageIdx(): Int {
        return pageIdx
    }

    override fun getInitialName(): String {
        return getTitle()
    }

    override fun rename(newName: String) {
        this.title = newName
    }
}