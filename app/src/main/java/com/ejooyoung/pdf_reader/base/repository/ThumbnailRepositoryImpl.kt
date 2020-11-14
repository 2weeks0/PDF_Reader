package com.ejooyoung.pdf_reader.base.repository

import android.app.Application
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.ThumbnailDataSource
import com.ejooyoung.pdf_reader.model.Thumbnail
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

class ThumbnailRepositoryImpl private constructor(
    private val thumbnailDataSource: ThumbnailDataSource
) : ThumbnailRepository {

    companion object {
        private var INSTANCE: ThumbnailRepository? = null

        fun getInstance(application: Application): ThumbnailRepository {
            if (INSTANCE == null) {
                INSTANCE = ThumbnailRepositoryImpl(DatabaseProvider.provideThumbnailSource(application))
            }
            return INSTANCE!!
        }
    }

    override fun selectThumbnail(thumbGuid: String): Maybe<Thumbnail?> {
        return thumbnailDataSource.selectThumbnail(thumbGuid)
            .onErrorReturn { null }!!
    }

    override fun insertThumbnails(vararg thumbnail: Thumbnail): Completable {
        return thumbnailDataSource.insertThumbnails(*thumbnail)
            .onErrorComplete()!!
    }

    override fun deleteThumbnails(vararg thumbnail: Thumbnail): Completable {
        return thumbnailDataSource.deleteThumbnails(*thumbnail)
            .onErrorComplete()!!
    }
}