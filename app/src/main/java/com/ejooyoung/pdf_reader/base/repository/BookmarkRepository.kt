package com.ejooyoung.pdf_reader.base.repository

import com.ejooyoung.pdf_reader.database.model.Bookmark
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

interface BookmarkRepository {
    fun selectAllBookmarks(bookGuid: String): Flowable<List<Bookmark>>
    fun insertBookmark(pageIdx: Long, bookGuid: String): Completable
    fun deleteBookmark(pageIdx: Long, bookGuid: String): Completable
    fun deleteBookmark(bookGuid: String): Completable
    fun updateBookmark(bookmark: Bookmark): Completable
    fun isBookmarkedPage(bookGuid: String, pageIdx: Long): Flowable<Boolean>
}