package com.ejooyoung.pdf_reader.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ejooyoung.pdf_reader.base.Const
import com.ejooyoung.pdf_reader.base.utils.DateUtils
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = Const.DB_BOOK_TABLE)
data class Book(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = Const.DB_BOOK_COLUMN_GUID)
    val guid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = Const.DB_BOOK_COLUMN_FILE_NAME)
    var fileName: String,
    @ColumnInfo(name = Const.DB_BOOK_COLUMN_URI)
    val uriString: String,
    @ColumnInfo(name = Const.DB_BOOK_COLUMN_THUMBNAIL_GUID)
    var thumbnailGuid: String,
    @ColumnInfo(name = Const.DB_BOOK_COLUMN_LAST_PAGE)
    var lastPage: Int = 0,
    @ColumnInfo(name = Const.DB_BOOK_COLUMN_CURRENT_PAGE)
    var currentPage: Int = 0,
    @ColumnInfo(name = Const.DB_BOOK_COLUMN_READ_TIME)
    var readTime: String = DateUtils.getCurrentTimeToDate()
) : Parcelable {
    companion object {
        fun valueOf(
            fileName: String,
            uriString: String,
            thumbnailGuid: String
        ) =
            Book(
                fileName = fileName,
                uriString = uriString,
                thumbnailGuid = thumbnailGuid
            )
    }

    fun getReadingPercentage(): Int {
        return 100 * (currentPage + 1) / lastPage
    }
}