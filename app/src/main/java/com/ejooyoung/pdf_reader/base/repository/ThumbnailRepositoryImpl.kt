package com.ejooyoung.pdf_reader.base.repository

import android.content.Context
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import com.ejooyoung.pdf_reader.database.dao.ThumbnailDao
import com.ejooyoung.pdf_reader.database.model.Thumbnail
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

class ThumbnailRepositoryImpl private constructor(
    private val thumbnailDao: ThumbnailDao
) : ThumbnailRepository {

    companion object {
        private var INSTANCE: ThumbnailRepository? = null

        fun getInstance(context: Context): ThumbnailRepository {
            if (INSTANCE == null) {
                INSTANCE = ThumbnailRepositoryImpl(DatabaseProvider.provideThumbnailSource(context))
            }
            return INSTANCE!!
        }
    }

    override fun selectThumbnail(thumbGuid: String): Maybe<Thumbnail?> {
        return thumbnailDao.selectThumbnail(thumbGuid)
            .onErrorReturn { null }!!
    }

    override fun insertThumbnails(vararg thumbnail: Thumbnail): Completable {
        return thumbnailDao.insertThumbnails(*thumbnail)
            .onErrorComplete()!!
    }

    override fun deleteThumbnails(vararg thumbnail: Thumbnail): Completable {
        return thumbnailDao.deleteThumbnails(*thumbnail)
            .onErrorComplete()!!
    }
}