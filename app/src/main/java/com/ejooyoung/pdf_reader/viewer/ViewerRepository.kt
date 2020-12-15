package com.ejooyoung.pdf_reader.viewer

import com.ejooyoung.pdf_reader.base.repository.BookmarkRepository
import com.ejooyoung.pdf_reader.database.model.Book
import com.ejooyoung.pdf_reader.viewer.scrollhandler.setting.ViewerSettingRepository
import io.reactivex.rxjava3.core.Completable

interface ViewerRepository: BookmarkRepository, ViewerSettingRepository {
    fun updateBook(book: Book): Completable
}