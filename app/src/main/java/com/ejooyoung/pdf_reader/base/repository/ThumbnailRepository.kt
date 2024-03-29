package com.ejooyoung.pdf_reader.base.repository

import com.ejooyoung.pdf_reader.database.model.Thumbnail
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface ThumbnailRepository {
    fun selectThumbnail(thumbGuid: String): Maybe<Thumbnail?>
    fun insertThumbnails(vararg thumbnail: Thumbnail): Completable
    fun deleteThumbnails(vararg thumbnail: Thumbnail): Completable
}