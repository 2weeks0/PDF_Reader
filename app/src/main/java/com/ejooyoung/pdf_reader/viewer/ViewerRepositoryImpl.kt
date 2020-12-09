package com.ejooyoung.pdf_reader.viewer

import android.content.Context
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.base.repository.BookmarkRepository
import com.ejooyoung.pdf_reader.base.repository.BookmarkRepositoryImpl
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Bookmark
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

class ViewerRepositoryImpl private constructor(
    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository
): ViewerRepository {

    companion object {
        fun newInstance(context: Context): ViewerRepository {
            return ViewerRepositoryImpl(
                BookRepositoryImpl.getInstance(context),
                BookmarkRepositoryImpl.getInstance(context)
            )
        }
    }

    override fun updateBook(book: Book): Completable {
        return bookRepository.updateBook(book)
    }

    override fun selectAllBookmarks(bookGuid: String): Flowable<List<Bookmark>> {
        return bookmarkRepository.selectAllBookmarks(bookGuid)
    }

    override fun insertBookmark(pageIdx: Int, bookGuid: String): Completable {
        return bookmarkRepository.insertBookmark(pageIdx, bookGuid)
    }

    override fun deleteBookmark(pageIdx: Int, bookGuid: String): Completable {
        return bookmarkRepository.deleteBookmark(pageIdx, bookGuid)
    }

    override fun deleteBookmark(bookGuid: String): Completable {
        return bookmarkRepository.deleteBookmark(bookGuid)
    }

    override fun updateBookmark(bookmark: Bookmark): Completable {
        return bookmarkRepository.updateBookmark(bookmark)
    }

    override fun isBookmarkedPage(bookGuid: String, pageIdx: Int): Flowable<Boolean> {
        return bookmarkRepository.isBookmarkedPage(bookGuid, pageIdx)
    }
}