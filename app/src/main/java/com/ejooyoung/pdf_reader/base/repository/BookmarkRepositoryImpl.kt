package com.ejooyoung.pdf_reader.base.repository

import android.content.Context
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.dao.BookmarkDao
import com.ejooyoung.pdf_reader.database.model.Bookmark
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class BookmarkRepositoryImpl private constructor(
    private val bookmarkDao: BookmarkDao
): BookmarkRepository {

    companion object {
        private var INSTANCE: BookmarkRepository? = null

        fun getInstance(context: Context): BookmarkRepository {
            if (INSTANCE == null) {
                INSTANCE = BookmarkRepositoryImpl(DatabaseProvider.provideBookmarkSource(context))
            }
            return INSTANCE!!
        }
    }

    override fun selectAllBookmarks(bookGuid: String): Flowable<List<Bookmark>> {
        return bookmarkDao.selectAllBookmarks(bookGuid)
    }

    override fun insertBookmark(pageIdx: Long, bookGuid: String): Completable {
        return bookmarkDao.insertBookmarks(Bookmark.valueOf(pageIdx, bookGuid))
    }

    override fun deleteBookmark(pageIdx: Long, bookGuid: String): Completable {
        return bookmarkDao.deleteBookmark(pageIdx, bookGuid)
    }

    override fun deleteBookmark(bookGuid: String): Completable {
        return bookmarkDao.deleteBookmark(bookGuid)
    }

    override fun updateBookmark(bookmark: Bookmark): Completable {
        return bookmarkDao.updateBookmark(bookmark)
    }

    override fun isBookmarkedPage(bookGuid: String, pageIdx: Long): Flowable<Boolean> {
        return bookmarkDao.selectIsBookmarkedPage(bookGuid, pageIdx)
    }
}