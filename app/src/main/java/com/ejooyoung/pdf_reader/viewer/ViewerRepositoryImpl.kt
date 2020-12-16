package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import android.content.Context
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.base.repository.BookRepository
import com.ejooyoung.pdf_reader.base.repository.BookRepositoryImpl
import com.ejooyoung.pdf_reader.base.repository.BookmarkRepository
import com.ejooyoung.pdf_reader.base.repository.BookmarkRepositoryImpl
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Bookmark
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingRepository
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingRepositoryImpl
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import java.util.*

class ViewerRepositoryImpl private constructor(
    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val viewerSettingRepository: ViewerSettingRepository
): ViewerRepository {

    companion object {
        fun newInstance(context: Context): ViewerRepository {
            return ViewerRepositoryImpl(
                BookRepositoryImpl.getInstance(context),
                BookmarkRepositoryImpl.getInstance(context),
                ViewerSettingRepositoryImpl.newInstance(context.applicationContext as Application)
            )
        }
    }

    override fun updateBook(book: Book): Completable {
        return bookRepository.updateBook(book)
    }

    override fun selectAllBookmarks(bookGuid: String): Flowable<List<Bookmark>> {
        return bookmarkRepository.selectAllBookmarks(bookGuid)
    }

    override fun insertBookmark(bookmark: Bookmark): Completable {
        return bookmarkRepository.insertBookmark(bookmark)
    }

    override fun deleteBookmark(pageIdx: Int, bookGuid: String): Completable {
        return bookmarkRepository.deleteBookmark(pageIdx, bookGuid)
    }

    override fun deleteBookmark(bookGuid: String): Completable {
        return bookmarkRepository.deleteBookmark(bookGuid)
    }

    override fun deleteBookmark(bookmark: Bookmark): Completable {
        return bookmarkRepository.deleteBookmark(bookmark)
    }

    override fun updateBookmark(bookmark: Bookmark): Completable {
        return bookmarkRepository.updateBookmark(bookmark)
    }

    override fun isBookmarkedPage(bookGuid: String, pageIdx: Int): Flowable<Boolean> {
        return bookmarkRepository.isBookmarkedPage(bookGuid, pageIdx)
    }

    override fun savePreference(viewerPreference: ViewerPreference, value: Boolean): Completable {
        return viewerSettingRepository.savePreference(viewerPreference, value)
    }

    override fun loadAllPreference(): Observable<EnumMap<ViewerPreference, Boolean>> {
        return viewerSettingRepository.loadAllPreference()
    }
}