package com.ejooyoung.pdf_reader

import android.app.Application
import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ejooyoung.pdf_reader.database.DatabaseProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File


object BindingAdapter {

    private const val RADIUS_THUMB = 8

    @JvmStatic
    @BindingAdapter("thumbGuid")
    fun setThumbnail(iv: ImageView, thumbGuid: String) {
        (iv.context.applicationContext as Application).let {
            DatabaseProvider.provideThumbnailSource(it).selectThumbnail(thumbGuid)
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
    @BindingAdapter("layoutWidth", "layoutHeight", "layoutMargin", "isHorizontal")
    fun setWidth(view: View, widthProgress: Int, heightProgress: Int, marginProgress: Int, isHorizontal: Boolean) {
        val displayMetrics: DisplayMetrics = view.resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels
        val dpWidth = displayMetrics.widthPixels

        view.layoutParams = (view.layoutParams as RelativeLayout.LayoutParams).apply {
            if (isHorizontal) {
                width = widthProgress * (dpWidth / 3) / 100
                height = heightProgress * dpHeight / 100
                bottomMargin = marginProgress * (dpHeight - height) / 100
                leftMargin = 0
            }
            else {
                width = heightProgress * dpWidth / 100
                height = widthProgress * (dpHeight / 3) / 100
                bottomMargin = 0
                leftMargin = marginProgress * (dpWidth - width) / 100
            }

            if (view.id == R.id.leftTouchZone) {
                addRule(RelativeLayout.ALIGN_PARENT_TOP, if (isHorizontal) 0 else RelativeLayout.TRUE)
                addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, if (isHorizontal) RelativeLayout.TRUE else 0)
            }
            else {
                addRule(RelativeLayout.ALIGN_PARENT_START, if (isHorizontal) 0 else RelativeLayout.TRUE)
                addRule(RelativeLayout.ALIGN_PARENT_END, if (isHorizontal) RelativeLayout.TRUE else 0)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("glideRequest", "bitmap")
    fun setBitmap(imageView: ImageView, glideRequest: RequestManager, bitmap: Bitmap?) {
        if (bitmap == null) {
            return
        }
        glideRequest.load(bitmap)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .thumbnail(0.2f)
            .into(imageView)
    }
}