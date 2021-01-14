package com.ejooyoung.pdf_reader.base.ext

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.res.ResourcesCompat
import com.ejooyoung.pdf_reader.R

fun Bitmap.withBorder(
    resources: Resources,
    borderSize: Int = 2,
    borderColor: Int = R.color.bg_thumb_border
): Bitmap {
    return withBorder(borderSize, ResourcesCompat.getColor(resources, borderColor, null))
}

fun Bitmap.withBorder(borderSize: Int, borderColor: Int): Bitmap {
    val result = Bitmap.createBitmap(
        width + borderSize * 2,
        height + borderSize * 2,
        config
    )
    Canvas(result).let {
        it.drawColor(borderColor)
        it.drawBitmap(this, borderSize.toFloat(), borderSize.toFloat(), null)
    }
    this.recycle()
    return result
}