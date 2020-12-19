package com.ejooyoung.pdf_reader

import android.app.Application
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ejooyoung.pdf_reader.base.repository.ThumbnailRepositoryImpl
import com.ejooyoung.pdf_reader.base.utils.DevLogger
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
                    if (File(thumb?.getAbsolutePath()).exists()) {
                        Glide.with(iv)
                            .load(thumb?.getAbsolutePath())
                            .transform(MultiTransformation(CenterCrop(), RoundedCorners(RADIUS_THUMB)))
                            .into(iv)
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("layoutWidth", "layoutHeight", "layoutMargin")
    fun setWidth(view: View, widthProgress: Int, heightProgress: Int, marginProgress: Int) {
        val displayMetrics: DisplayMetrics = view.resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels
        val dpWidth = displayMetrics.widthPixels

        view.layoutParams = (view.layoutParams as RelativeLayout.LayoutParams).apply {
            width = widthProgress * (dpWidth / 3) / 100
            height = heightProgress * dpHeight / 100
            bottomMargin = marginProgress * (dpHeight - height) / 100
            DevLogger.d("width: $width, height: $height, margin: $bottomMargin")
        }
    }
}