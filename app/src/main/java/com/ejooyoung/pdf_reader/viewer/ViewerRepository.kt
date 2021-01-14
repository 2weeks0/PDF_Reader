package com.ejooyoung.pdf_reader.viewer

import android.content.Context
import android.graphics.Bitmap
import com.ejooyoung.pdf_reader.base.repository.BookmarkRepository
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingRepository
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.touchzone.SettingTouchZoneRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

interface ViewerRepository : BookmarkRepository, ViewerSettingRepository, SettingTouchZoneRepository {
    fun updateBook(book: Book): Completable
    fun loadThumbnail(context: Context, index: Int): Observable<Bitmap>
}