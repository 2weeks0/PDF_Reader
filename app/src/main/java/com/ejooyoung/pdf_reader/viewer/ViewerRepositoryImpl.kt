package com.ejooyoung.pdf_reader.viewer

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import com.ejooyoung.pdf_reader.application.preference.ViewerPreference
import com.ejooyoung.pdf_reader.application.preference.ViewerPreferenceMap
import com.ejooyoung.pdf_reader.base.utils.ThumbnailUtils
import com.ejooyoung.pdf_reader.base.utils.UnitUtils
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.dao.BookDao
import com.ejooyoung.pdf_reader.database.dao.BookmarkDao
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.database.model.Bookmark
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingRepository
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingRepositoryImpl
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.SettingTouchZoneRepository
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.SettingTouchZoneRepositoryImpl
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.model.TouchZone
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable

class ViewerRepositoryImpl private constructor(
    private val bookDao: BookDao,
    private val bookmarkDao: BookmarkDao,
    private val viewerSettingRepository: ViewerSettingRepository,
    private val settingTouchZoneRepository: SettingTouchZoneRepository
): ViewerRepository {

    companion object {
        fun newInstance(context: Context): ViewerRepository {
            return ViewerRepositoryImpl(
                DatabaseProvider.provideBookSource(context),
                DatabaseProvider.provideBookmarkSource(context),
                ViewerSettingRepositoryImpl.newInstance(context.applicationContext as Application),
                SettingTouchZoneRepositoryImpl.newInstance(context.applicationContext as Application)
            )
        }
    }

    override fun updateBook(book: Book): Completable {
        return bookDao.updateBook(book)
    }

    override fun selectAllBookmarks(bookGuid: String): Flowable<List<Bookmark>> {
        return bookmarkDao.selectAllBookmarks(bookGuid)
    }

    override fun insertBookmark(bookmark: Bookmark): Completable {
        return bookmarkDao.insertBookmarks(bookmark)
    }

    override fun deleteBookmark(pageIdx: Int, bookGuid: String): Completable {
        return bookmarkDao.deleteBookmark(pageIdx, bookGuid)
    }

    override fun deleteBookmark(bookGuid: String): Completable {
        return bookmarkDao.deleteBookmark(bookGuid)
    }

    override fun deleteBookmark(bookmark: Bookmark): Completable {
        return bookmarkDao.deleteBookmark(bookmark)
    }

    override fun updateBookmark(bookmark: Bookmark): Completable {
        return bookmarkDao.updateBookmark(bookmark)
    }

    override fun isBookmarkedPage(bookGuid: String, pageIdx: Int): Flowable<Boolean> {
        return bookmarkDao.selectIsBookmarkedPage(bookGuid, pageIdx)
    }

    override fun savePreference(viewerPreference: ViewerPreference, value: Boolean): Completable {
        return viewerSettingRepository.savePreference(viewerPreference, value)
    }

    override fun loadAllPreference(): Observable<ViewerPreferenceMap> {
        return viewerSettingRepository.loadAllPreference()
    }

    override fun loadTouchZonePreference(touchZone: TouchZone): Completable {
        return settingTouchZoneRepository.loadTouchZonePreference(touchZone)
    }

    override fun saveTouchZonePreference(touchZone: TouchZone): Completable {
        return settingTouchZoneRepository.saveTouchZonePreference(touchZone)
    }

    override fun loadThumbnail(context: Context, index: Int): Observable<Bitmap> {
        return Observable.fromCallable {
            ThumbnailUtils.getThumbnail(context, index, UnitUtils.dpToPx(context, 200f))
        }
    }
}