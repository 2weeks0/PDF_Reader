package com.ejooyoung.pdf_reader

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.ejooyoung.pdf_reader.model.PDF
import java.io.File

object BindingAdapter {

    const val RADIUS_THUMB = 8

    @JvmStatic
    @BindingAdapter("setPdfForThumbnail")
    fun setThumbnail(iv: AppCompatImageView, pdf: PDF) {
        val thumbPath = (iv.context.applicationContext as MainApplication).getThumbPath(pdf.name)
        if (File(thumbPath).exists()) {
            Glide.with(iv)
                .load(thumbPath)
                .transform(MultiTransformation(CenterCrop(), RoundedCorners(RADIUS_THUMB)))
                .into(iv)
        }
    }
}