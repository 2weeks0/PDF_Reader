package com.ejooyoung.pdf_reader.base.ext

import android.graphics.Bitmap
import android.graphics.Canvas

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
    return result
}