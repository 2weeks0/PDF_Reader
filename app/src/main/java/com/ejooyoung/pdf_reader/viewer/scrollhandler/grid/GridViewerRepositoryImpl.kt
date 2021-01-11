package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.app.Application
import android.graphics.Bitmap
import androidx.core.content.res.ResourcesCompat
import com.ejooyoung.pdf_reader.R
import com.ejooyoung.pdf_reader.base.ext.withBorder
import com.ejooyoung.pdf_reader.base.utils.ThumbnailUtils
import com.ejooyoung.pdf_reader.database.model.Book
import io.reactivex.rxjava3.core.Observable

class GridViewerRepositoryImpl private constructor(): GridViewerRepository {

    companion object {
        fun newInstance(): GridViewerRepository {
            return GridViewerRepositoryImpl()
        }
    }

    override fun getThumbnail(application: Application, book: Book, index: Int): Observable<Bitmap> {
        return Observable.fromCallable {
            ThumbnailUtils.getThumbnail(application, book, index)
                .withBorder(
                    2,
                    ResourcesCompat.getColor(application.resources, R.color.bg_thumb_border, null)
                )
        }
    }
}