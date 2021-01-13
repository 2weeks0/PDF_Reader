package com.ejooyoung.pdf_reader.viewer.scrollhandler.grid

import android.content.Context
import android.graphics.Bitmap
import com.ejooyoung.pdf_reader.base.utils.ThumbnailUtils
import io.reactivex.rxjava3.core.Observable

class GridViewerRepositoryImpl private constructor(): GridViewerRepository {

    companion object {
        fun newInstance(): GridViewerRepository {
            return GridViewerRepositoryImpl()
        }
    }

    override fun getThumbnail(context: Context, index: Int): Observable<Bitmap> {
        return Observable.fromCallable {
            ThumbnailUtils.getThumbnail(context, index)
        }
    }
}