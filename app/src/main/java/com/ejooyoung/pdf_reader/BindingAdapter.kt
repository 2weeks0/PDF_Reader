package com.ejooyoung.pdf_reader

import android.app.Application
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ejooyoung.pdf_reader.base.repository.ThumbnailRepositoryImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File

object BindingAdapter {

    private const val RADIUS_THUMB = 8

    @JvmStatic
    @BindingAdapter("thumbGuid")
    fun setThumbnail(iv: ImageView, thumbGuid: String) {
        (iv.context.applicationContext as Application).let {
            ThumbnailRepositoryImpl.getInstance(it).selectThumbnail(thumbGuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {thumb ->
                    if (File(thumb.getAbsolutePath()).exists()) {
                        Glide.with(iv)
                            .load(thumb.getAbsolutePath())
                            .transform(MultiTransformation(CenterCrop(), RoundedCorners(RADIUS_THUMB)))
                            .into(iv)
                }
            }
        }
    }
}